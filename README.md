
# Rick and Morty Android World

This app is a playground for me to learn about the latest android libraries by building a modern android app.
This app consumes the https://rickandmortyapi.com/ to show the characters, locations and episodes details of the mad scientist series, *_Rick and Morty._*

Work In Progress 🚧👷🔧️👷‍♀️⛏


## Tech Stack

- 100% Kotlin + RXjava3 - perform background operations
- Retrofit - networking
### Jetpack
- LiveData - notify views about database change
- ViewModel - store and manage UI-related data in a lifecycle conscious way
- Hilt Dagger - dependency injection
- Timber - logging
### Modern Architecture
- MVVM Architecture (View - DataBinding - ViewModel - Model)
- Android Architecture components (ViewModel, LiveData)
- Android KTX - Jetpack Kotlin extensions


## Setup and Installation

Follow the below steps to setup the project on your mac:


```bash
  # Clone the project 
  git clone https://github.com/arunm619/Rickandmortyapp
  cd Rickandmortyapp
  
  # create local.properties file and add your SDK path here 
  # dont forget to update your USERNAME
  echo "sdk.dir = /Users/USERNAME/Library/Android/sdk" > local.properties

  # Now comes the fun part, build your apk with the following command 
  ./gradlew assembleDebug

  # The APK resides in this path
  cd Rickandmortyapp/app/build/outputs/apk

```
    
## Authors

- [@arunm619](https://www.github.com/arunm619)


## Licenses


[![MIT License](https://img.shields.io/apm/l/atomic-design-ui.svg?)](https://github.com/tterb/atomic-design-ui/blob/master/LICENSEs)

