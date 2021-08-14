<h1 align="center">Foody</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
   <a href="https://github.com/aptx113/Foody/actions"><img alt="Build Status" src="https://github.com/aptx113/Foody/actions/workflows/build.yml/badge.svg"/></a> 
  <a href="https://github.com/aptx113"><img alt="Profile" src="https://img.shields.io/static/v1?label=GitHub&message=aptx113&color=blue&logo=github"/></a> 
</p>

<p align="center">
Foody is a demo application based on modern Android application tech-stacks and MVVM architecture.<br>This project is for focusing especially on the new library Hilt of implementing dependency injection.<br>
Also fetching data from the network and integrating persisted data in the database via repository pattern.
</p>

## Download
Go to the [Releases](https://github.com/aptx113/Foody/releases) to download the latest APK.
## Feature
1. Search recipes by selecting chips or entering keyword.

<img width="300" height="600" src="https://github.com/aptx113/Foody/blob/create_readme/preview/Foody_Recipe_search.gif"/>

2. Navigate to Details to check more recipe info.
<p align="left">
<img width="300" height="600" src="https://github.com/aptx113/Foody/blob/create_readme/preview/Foody_Details.gif"/>
</p>

3. Add recipes to Favorite with multiple selection and deletion.

<img width="300" height="600" src="https://github.com/aptx113/Foody/blob/create_readme/preview/Foody_Favorite.gif"/>

4. Randomly show food joke.

<img width="300" height="600" src="https://github.com/aptx113/Foody/blob/create_readme/preview/Foody_Joke.gif"/>

5. Display the last Recipe list when offline through cache.

<img width="300" height="600" src="https://github.com/aptx113/Foody/blob/create_readme/preview/Foody_Offline_Cache.gif"/>

## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack
  - [Constraintlayout](https://developer.android.com/training/constraint-layout/motionlayout) - Construct MotionLayout to animate view.
  - [Databinding](https://developer.android.com/topic/libraries/data-binding) - Bind UI components in the layouts to data sources in the app.
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Store user previously selected data asynchronously, consistently, and transactionally, overcoming some of the drawbacks of SharedPreferences.
  - [Hilt](https://dagger.dev/hilt/) - For dependency injection.
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Build lifecycle-aware components, like ViewModel and LiveData that can automatically adjust their behavior based on the current lifecycle state of an activity or fragment.
  - [Navigation](https://developer.android.com/guide/navigation) - Navigate across, into, and back out from the different pieces of content within the app.
  - [Recyclerview](https://developer.android.com/guide/topics/ui/layout/recyclerview) - Use to efficiently display large sets of data.
  - [Room](https://developer.android.com/training/data-storage/room) - Construct a database using the abstract layer for offline cache.
  - [ViewPager2](https://developer.android.com/guide/navigation/navigation-swipe-view-2) - Display Details in a swipeable format.
- Third Party Library
  - [Coil](https://github.com/coil-kt/coil) - Loading images.
  - [Jsoup](https://github.com/jhy/jsoup) - To parse HTML.
  - [Moshi](https://github.com/square/moshi) - Parse and convert a JSON object into Kotlin objects.
  - [OkHttp3](https://github.com/square/okhttp) - Log the outgoing request and the incoming response.
  - [Retrofit2](https://github.com/square/retrofit) - Send requests to API and receive response.
  - [Timber](https://github.com/JakeWharton/timber) - For logging.
- [Material](https://github.com/material-components/material-components-android) - Help to build material components like bottom navigation bar, floating action button.
- Architectural and Design pattern
  - MVVM
  - Observer
  - Adapter
  - Dependency Injection
  - Singleton

## MAD Score
![summary](https://user-images.githubusercontent.com/56501046/129439729-0ddb2de0-77ec-4a8c-924a-5c6bc7dccc40.jpg)
![kotlin](https://user-images.githubusercontent.com/56501046/129439743-8912c545-4448-4296-849e-2075adfae776.jpg)

## Open API

<img src="https://user-images.githubusercontent.com/56501046/129440570-48ffd12e-c6fa-426e-960f-7d88e8739d8b.png" align="right" height="10%" 
width="10%"/>

Foody using the [Spoonacular API](https://spoonacular.com/food-api) for obtaining remote data.<br>
Spoonacular API provides a RESTful API interface to highly detailed objects built from plenty of lines of data related to Food Recipes.

