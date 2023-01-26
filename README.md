# Receipt Scanner

## Table of Contents

+ [About](#about)
+ [Getting Started](#getting_started)
+ [Usage](#usage)
+ [Built Using](#built_using)

## 🧐 About <a name = "about"></a>

An android app developed in Kotlin and uses MLKit's Text Recognizer to scan receipts, recognize texts from the scan, store and retreive the text information from Room database.

## 🏁 Getting Started <a name = "getting_started"></a>

To get this application up and running in your mobile device, download this [apk](https://drive.google.com/drive/folders/1bZZO4OSPRHlOLamu_8oQmBu0SbPc_YcL) and install it in your mobile device.

## 🎈 Usage <a name = "usage"></a>

After launching the applicaation, give the permissions for the app to access your file manager and camera.

![Alt text](https://github.com/CaptanoMaina/Receipt-Scanner/blob/master/image%20source/perm1.jpeg?raw=true)

![Alt text](image%20source/perm2.jpeg)

Select an image from the gallery or launch the camera to take a picture.

![Alt text](image%20source/gallery.jpeg)

Once the image is selected, recognize the text by pressing the recognize text button.

![<img src = "image%20source/recog.jpeg" width ="70"/>](image%20source/recog.jpeg)

If you are satisfied with the results of the scan click on the Save Record button.

To view previously saved details, click on the history icon at the top right and the saved data will be arraanged in descending order of the dates it was saved.

![Alt text](image%20source/list.jpeg)

## ⛏️ Built Using <a name = "built_using"></a>

+ [RoomDB](https://developer.android.com/jetpack/androidx/releases/room/) - Database
+ [Kotlin](https://kotlinlang.org/) - Programming Language
+ [MLKit](https://developers.google.com/ml-kit/) - Text Recognition
