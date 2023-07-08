## **Rick & Morty Collection App**
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

### **HomeActivity**
### **RickMortyApplication**
    
