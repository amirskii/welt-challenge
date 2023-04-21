# Patronus Android Challenge

Following technologies have been used:
1. Architecture Components (Coroutine/Flow, ViewModel, Navigation)
2. MVVM, Koin for dependency injection
3. Kotlin written
4. SOLID, Clean Architecture
5. Viewmodel, Use Case, Data transformation, Gateway layers covered with Unit-tests

## Clean Architecture
Although challenge project is tiny I tried to separate business logic, presentation and data layers as best as possible.

### Layers
- **Domain** - Contains the business logic of the application: use cases and domain models.
- **Data** - Network interraction and data models.
- **Presentation** - A layer that interacts with the UI and Android framework has also own models. 


## Screenshots

[<img src="/screenshots/list.png" align="left" width="200" hspace="10" vspace="10">](/screenshots/list.png)
[<img src="/screenshots/map.png" align="center" width="200" hspace="10" vspace="10">](/screenshots/map.png)

### Unit tests
[<img src="/screenshots/tests.png" align="left" width="400" hspace="30" vspace="10">](/screenshots/tests.png)
