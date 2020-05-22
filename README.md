# StatesOfIndia

StatesOfIndia is a simple quiz app that checks your knowledge on the Capitals of the States of India.

## Features
The app contains a list of all the States and Union Territories of India. The list can be modified and entries can be updated. The stats of the quiz are stored and can be reset. The list can be sorted in multiple ways. It also supports Light and Dark Themes.

## Architecture
The architecture is built around [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/). The app is built using the latest AndroidX and Jetpack libraries.
The logic is kept away from Activities and Fragments and moved it to [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)s following the MVVM Architecture and inline with the best practice. The data is observed using [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) and update changes in the UI.

[Navigation component](https://developer.android.com/guide/navigation) is used to simplify into a single Activity app.<br><br>
[NavGraph](https://developer.android.com/reference/androidx/navigation/NavGraph) is used to simplify the transition between fragments.<br><br>
[Room](https://developer.android.com/jetpack/androidx/releases/room) is used to store the list of states and its details.<br><br>
The app uses [Transformations](https://developer.android.com/reference/android/arch/lifecycle/Transformations) to support multiple sorting options.<br><br>
The quiz stats are stored using [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences).<br><br>
The app uses [WorkManager](https://developer.android.com/jetpack/androidx/releases/work) to send a periodic notification that can be enabled in the settings.

## Screenshots

<p align="center">
  <br>
  <img src="https://user-images.githubusercontent.com/39412016/82646021-73e5ec00-9c31-11ea-8f6a-bde4be525a80.png" width="270" height="480">
  <img src="https://user-images.githubusercontent.com/39412016/82646013-71839200-9c31-11ea-977d-550f5709ae77.png" width="270" height="480">
  <img src="https://user-images.githubusercontent.com/39412016/82646025-75171900-9c31-11ea-92a8-5bfa6259e56f.png" width="270" height="480">
  <br><br>
  <img src="https://user-images.githubusercontent.com/39412016/82646022-747e8280-9c31-11ea-8f07-7b0667dd6787.png" width="270" height="480">
  <img src="https://user-images.githubusercontent.com/39412016/82646030-75afaf80-9c31-11ea-9496-ff685c938d40.png" width="270" height="480">
  <img src="https://user-images.githubusercontent.com/39412016/82646028-75afaf80-9c31-11ea-8c23-308d4a5a8853.png" width="270" height="480">
  <br><br>
   <img src="https://user-images.githubusercontent.com/39412016/82646023-747e8280-9c31-11ea-96be-07cf585ef16b.png" width="270" height="480">
   <img src="https://user-images.githubusercontent.com/39412016/82646019-734d5580-9c31-11ea-8d36-bf3af9d7942f.png" width="270" height="480">
</p>
