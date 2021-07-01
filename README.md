<h2 align="center">NewsBreeze Android Application</h2> 
<p align="center">
<img align="center" alt="logo" width="200px" height="200px" src="app/src/main/res/mipmap-xhdpi/app_icon.png" /></p>
NewsBreeze is a news application that selects latest and best news from Newsapi.org and summarises them to present in a short-summarized format. News is sourced from various categories and various sources, making sure that you always get the best.

### Features

- **Bookmarks**- Save News you dont't have time to read so that you can come back to them later.
- **Download**  - Download your favorite News while on Internet and enjoy your reading later without internet connection.
- **Search**    -  The latest news list has a search by title feature. Search lets you find any news by simply typing in a keyword in the search box. Easy access to your favorite topics of intrest via the search option
- **Read full article**- If you need full story of an article just click on the more button then you will be redirected to the source url.
- **Share**   - If you like a news, share it with your friends using whatsapp, email or message & more.
- **MVVM** with Android Architecture Components(Room, LiveData, ViewModel)


### News Categories
   NewsBreeze is having all types of news and headlines from topics like 
   - Bussiness
   - Entertainment
   - General
   - Health
   - Scince
   - Sports
   - Technology - all in one place

### Permissions
NewsBreeze requires the following permissions:
- Photos/Media Files    -The app needs access to the device's storage to save representational images, so that you have a good experience even without interenet connectivity.
- Internet connectivity - This application needs internet connectivity to retrive data from newsapi.org.

### Libraries Used
- [Retrofit](http://square.github.io/retrofit/) For sending network requests and parsing JSON data.
- [Picasso](https://square.github.io/picasso/) Loading and caching images.
- **Room** is a database layer on top of an SQLite database.
- **Lifecycle**

### Architecture
<img src="https://user-images.githubusercontent.com/52503391/124106194-9c215a80-da81-11eb-8cec-2b9ab8f7192a.png"/>

- **View** - Activity/Fragment with UI-specific logics only.
- **ViewModel** - It keeps the logic away from View layer, provides data streams for UI and handle user interactions. It servers as a link between the Model and the View.
- **Model** - This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.
- **Repository** -The repository is a mediator between the different data sources. ViewModel simply request data from the repository.


### Screenshots
| Main Screen | Full article |  Categories |
|:-:|:-:|:-:|
|![1](https://user-images.githubusercontent.com/52503391/124110996-70ed3a00-da86-11eb-87fe-e8381062da79.JPG) | ![2](https://user-images.githubusercontent.com/52503391/124110021-68483400-da85-11eb-817c-89184397d149.JPG) | ![3](https://user-images.githubusercontent.com/52503391/124110027-69796100-da85-11eb-9103-8a8a41cbdf61.JPG) |
| Share | Downloaded News |  Bookmarks |
| ![4](https://user-images.githubusercontent.com/52503391/124110033-6b432480-da85-11eb-9243-68bd53d6ade9.JPG) | ![5](https://user-images.githubusercontent.com/52503391/124110041-6c745180-da85-11eb-8a3e-81b7a501081d.JPG) | ![6](https://user-images.githubusercontent.com/52503391/124110044-6d0ce800-da85-11eb-9d92-4ede7bb99bf6.JPG)
 |




