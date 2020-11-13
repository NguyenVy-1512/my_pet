# Android Clean Architecture 

## Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* Android Support Libraries
* [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
* [Dagger 2 (2.21)](https://github.com/google/dagger)
* [Glide](https://github.com/bumptech/glide)
* [Retrofit](http://square.github.io/retrofit/)
* [OkHttp](http://square.github.io/okhttp/)
* [Gson](https://github.com/google/gson)
* [Timber](https://github.com/JakeWharton/timber)
* [Mockito](http://site.mockito.org/)
* [Robolectric](http://robolectric.org/)
* Android Navigation

## Requirements

* JDK 1.8
* [Android SDK](https://developer.android.com/studio/index.html)
* Latest Android SDK Tools and build tools.

## Architecture

### User Interface

This layer makes use of the Android Framework and is used to create all of our UI components

### Presentation

This layer's responsibility is to handle the presentation of the User Interface, but at the same time knows nothing about the user interface itself. This layer has no dependence on the Android Framework, it is a pure Kotlin module.

### Domain

The domain layer responsibility is to simply contain the UseCase instance used to retrieve data from the Data layer and pass it onto the Presentation layer.

### Data

The Data layer is our access point to external data layers and is used to fetch data from multiple sources (the cache and network in our case).

### Remote

The Remote layer handles all communications with remote sources
### Cache

The Cache layer handles all communication with the local database which is used to cache data. 

## Thanks

A special thanks to the authors involved with these two repositories, they were a great resource during our learning!
- https://github.com/bufferapp/android-clean-architecture-boilerplate

- https://github.com/android10/Android-CleanArchitecture

- https://github.com/googlesamples/android-architecture
