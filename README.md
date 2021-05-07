# SpaceXNews

## Business Requirement

  * Fetch list of spaceflight news item from Server
  * Search news list
  * Open news article in available browser
  
## Project Specification

  Project is developed using MVVM clean architecture in Kotlin language. App is getting list of spaceflight news from server and populating on UI. 
  For searching/filtering local storage(Room DB) with kotlin flow is getting used. For all network calls Coroutine has been used for background thread operations.
  For UI update Livedata is used.
  
## Libraries Used
  
  * Retrofit
  * Koin
  * Mockk
  * Kotlin Flow
  * Coroutine
  * Livedata
  * Glide
  
## Improvements/ Not Implemented

  * For large data set pagination can be implemented with offline first architecture for better performance
  * Code coverage can be improved with more unit test cases
  * Swipe to refresh
  * Local DB cache refresh based on business requirement
  * UI test cases

