package com.captano.rscan

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


class MainActivity : AppCompatActivity() {
    private lateinit var imageBtn: MaterialButton
    private lateinit var scanTextBtn: MaterialButton
    private lateinit var imageVi: ImageView
    private lateinit var recognizedTextEd: EditText

    private companion object {
        private const val CAMERA_REQUEST_CODE = 100
        private const val STORAGE_REQUEST_CODE = 101
    }

    //Image Uri from Camera/Gallery
    private var imageUri: Uri? = null

    //arrays for camera and gallery permissions
    private lateinit var cameraPermissions: Array<String>
    private lateinit var storagePermissions: Array<String>

    private lateinit var progressDialog: ProgressDialog

    private lateinit var textRecognizer: TextRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializing UI views
        imageBtn = findViewById(R.id.imageBtn)
        scanTextBtn = findViewById(R.id.scanTextBtn)
        imageVi = findViewById(R.id.imageVi)
        recognizedTextEd = findViewById(R.id.recognizedTextEd)


        //initializing camera and gallery permissions
        cameraPermissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        //initialize progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //initialize Text Recognizer
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)


        //onClickListener to show image input dialog
        imageBtn.setOnClickListener {
            showInputImageDialog()
        }

        //onClickListener to scan for text in image
        scanTextBtn.setOnClickListener {
            if (imageUri == null) {

                showToast("Pick Image...")

            } else {

                recognizedTextFromImage()

            }
        }


    }

    private fun recognizedTextFromImage() {
        progressDialog.setMessage("Preparing Image For Processing..")
        progressDialog.show()

        try {

            val inputImage = InputImage.fromFilePath(this, imageUri!!)

            progressDialog.setMessage("Scanning Image For Text...")

            //starting text recognition
            val textTaskResult = textRecognizer.process(inputImage)
                .addOnSuccessListener { text ->

                    progressDialog.dismiss()

                    val recognizedText = text.text

                    recognizedTextEd.setText(recognizedText)

                }


                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    showToast("Failed to recognize text due to ${e.message}")
                }

        } catch (e: Exception) {
            progressDialog.dismiss()
            showToast("Failed to prepare image due to ${e.message}")
        }

    }

    private fun showInputImageDialog() {
        val popupMenu = PopupMenu(this, imageBtn)

        popupMenu.menu.add(Menu.NONE, 1, 1, "Camera")
        popupMenu.menu.add(Menu.NONE, 2, 2, "Gallery")

        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { menuItem ->

            val id = menuItem.itemId
            if (id == 1) {

                //check if camera permissions are granted

                if (checkCameraPermission()) {
                    pickImage()
                } else {
                    requestCameraPermission()
                }

            } else if (id == 2) {
                //gallery is selected, check for permissions

                if (checkStoragePermission()) {
                    uploadImage()
                } else {
                    requestStoragePermission()
                }

            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data!!.data

                imageVi.setImageURI(imageUri)
            } else {
                showToast("Cancelled...!")

            }
        }


    private fun pickImage() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Sample Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Description")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)

    }


    private val cameraActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageVi.setImageURI(imageUri)
            } else {
                showToast("Cancelled...!")

            }

        }


    private fun checkStoragePermission(): Boolean {

        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCameraPermission(): Boolean {

        val cameraResult = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val storageResult = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        return cameraResult && storageResult

    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE)
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted && storageAccepted) {
                        pickImage()
                    } else {
                        showToast("Camera and Storage permissions required....*")
                    }
                }

            }
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (storageAccepted) {
                        uploadImage()
                    } else {
                        showToast("Storage Permission required")
                    }


                }
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}