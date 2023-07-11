### Rick & Morty Collection App

App to browse characters, episodes and search details from Rick & Morty Tv show.

Check out the app here - [Download app](https://github.com/harisheoran/rick-morty-app/releases) 

---

#### Read about its MVVM architecture [here](https://sparrowbit.hashnode.dev/basic-mvvm-android-app).

#### Tech Stack & Libraries
- Kotlin Language
- MVVM Architecture 
- Retrofit
- Picasso
- Epoxy RecyclerView
- Paging 3
- Moshi

#### API
- [The Rick and Morty API](https://rickandmortyapi.com/)

---

### MVVM Architecture
![Alt text](arch.jpg "Image caption")

Start from Network Layer

#### 1. **Network Layer**

4 main components are

- Service Interface
    - It defines how the app should talk to the server
```
interface RickAndMortyService {

    @GET(value = "character/")
    suspend fun getCharactersPage(@Query(value = "page") pageIndex: Int): Response<GetCharactersPageResponse>
}        
```

- Retrofit service
    -  Creates an implementation of the API endpoint defined by our interface and pass that service to our API client.

 ```
 object NetworkLayer {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(getLoggingHttpClient())
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val rickAndMortyService: RickAndMortyService by lazy {
        retrofit.create(RickAndMortyService::class.java)
    }

    val apiClient = ApiClient(rickAndMortyService)
}
```

- API Client
    - Through which our app will talk to the server.
    - It has the methods to talk to API

- Network Error handling







writing...
--- 

File Structure

### **characters**
- *details* - 
    - CharacterFragment
    - CharacterDetailsEpoxyController
    - CharacterDetailsViewModel
    - CharacterDetailsRepository

- CharacterListFragment
- CharacterListPagingEpoxyController
- CharacterListPagingDataSource
- CharacterListViewModel
- CharacterListRepository

### **domain**
- *mappers*
    - CharacterMapper
    - EpisodeMapper

- *models*
    - Character
    - Episode

### **episodes**
- *details*
    - EpisodeDetailsFragment
    - EpisodeDetailsEpoxyController
    - EpisodeDetailsViewModel

- EpisodeListFragment
- EpisodeListPagingEpoxyContoller
- EpisodeListPagingSource
- EpisodeListViewModel
- EpisodeListRepository
- EpisodeUIModel

### **epoxy**
- LoadingEpoxyModel
- ViewBindingKotlinModel

### **network**
- *response*
    - GetCharacterByIdResponse
    - GetCharactersPageResponse
    - GetEpisodeByIdResponse
    - GetEpisodesPageResponse
    - PageInfo
- NetworkLayer
- RickAndMortyService
- ApiClient
- RickAndMortyCache
- SimpleResponse

#### **HomeActivity**
#### **RickMortyApplication**

---


## **MVVM Architecture**

- 



---
## **Network Debugging**



    
