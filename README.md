# MVVM Architecture (Kotlin)

MVVM architecture with Utils and Android Architecture Components (Android Jetpack), among others:
- Repository pattern with RxJava 2
- ViewModel
- LiveData
- Pagination library
- Room
- Google Map with ready to use clustering
- Example unit tests as with MockK
- Dependency Injection using Dagger 2
- DataBinding

## What is so nice about this architecture?

The included Android Architecture Components are not alone! 
Vanilla Android Architecture Components  sometimes doesn't work as we need too. I wrapped some of them to make it easier and faster to use.

### Enhanced Android Architecture Components

LiveData lacked "switchMap" like using Rx. LiveData also lacked "single events" so events will not be used once consumed. I created some utils for that.

Pagination lacked some callbacks about network status. It was also hard to easly switch call parameters (it is needed to create new pagination then). Those problems are solved using "ListingFactory" and it is really easy to, for example, create searching mechanism with pagination. We show the user that new data is being loaded and we create new pagination list each time user inputs new letters in search bar. Without the wrapper we would have to do some more work each time to make it working correctly.

### Routing system

Another thing is "routing system". Routers are here to help with communication between fragments with other fragments or with parent activity. More precisely commucation between ViewModels. It was hard sometimes to create communication between fragments - we need to get activity from fragment, then if activity is not null pass it the needed data and then pass it from activity to another fragment. It isn't really nice to write, it is buggable and hard to test. Another approach that I had seen was using EventBus for that. EventBus is indeed nice, but it is hard to maintain and it just "shouts" everywhere, so we can get events in places where we are shouldn't get. 

Routing system is easly testable, there is encapsulation and fragments in different activities can route events differently - for example we can ignore them or send to different fragments. Which event goes (or not) where decides Activity's router, so we don't really need to know in which activity fragment actually is, so we don't have to check which fragment's parent activity ist. It is more readable and clear than EventBus approach. Also thanks to live data and view model we can send event and show it to user without worrying about fragment's or activity's state. 

### Data binding for adapter items

For me it is not so nice to create data binding in xml, it is faster to create code using Kotlin. I have used dataBinding for adapter items, because most of the times it is easier to change the model in arrayList than to find position of it and do all the things to change look of the given view holder without data binding. 

## Pros

- Routing system makes communication with fragments and activities a lot easier, less bugable and more testable
- MVVM architecture with Android Architecture Components makes it easier to create code and we can worry less about fragment's and activity's lifecycle (as live data takes care of it)
- It is not library that puts limit on how do you use your architecture. It is your choice which given tools you will use and which not
- It has GoogleMapFragment with clustering and some utils for it ready to use (and you can create own functions for it)
- There is example with the most important parts of the architecture. You don't need to know Android Architecture Components from the start, it is quite easy to get grip on
- Junior with Kotlin knowledge should do fine with it copy pasting what he needs
- Example unit tests with MockK can be useful to begin journey with MockK! And it is easy to mock static classes!
- It has a lot of small extensions and utils that can make your life a little easier

## Cons
- At least one developer should know how Dagger 2 works. It is easy to copy-paste and the knowledge of Dagger is not really requied to do so, but just in case of expanding dependencies it would be wise to have at least one person that can do that
- If you want to use proposed repository pattern with RxJava it would be wise to have at least one person that understands basics of it. It is not needed to know this to use other parts of architecture (routing uses RxJava's PublishSubjects, but it is really easy to use)
- Needs some effort to write routing mechanism and if few developers add new routers there are some easy to fix git  conflicts when merging (due to changes in the same router class)

## Installation

To use this architecture you can either create new project and then write or copy parts of the architecture or pull repository and change package and application names

To change package name: in manifest right click on package name, refactor and then rename. Then change "application id" in gradle. 
More info: https://stackoverflow.com/questions/16804093/android-studio-rename-package

Make sure to have gitignore plugin installed and Android Studio's gitignore turned off before pushing newly created project to git to avoid pushing local config and other unneeded files. If your Android's Studio's gitignore is working then architecture gitignore will be ignored. To make it working:

1. Go to Settings > Version Control > Ignored Files and remove all ignored files. Hit Apply.
2. Still in Settings > Version Control change the type of version control from Git to none. Hit Apply.
3. Delete the project's .git directory created previously.
4. Create the following .gitignore file in the root directory of your project: https://stackoverflow.com/a/21049338/459095
5. VCS > Enable Version Control... to Re-enable Git.
6. Then right click on the project root and select Git > Add to add the entire project.

Doing the above will utilize the .gitignore file instead of Settings > Version Control > Ignored Files to commit the right files. https://stackoverflow.com/questions/29052342/android-studio-git-gitignore-vs-project-settings-version-control-ignored


## Possible improvements in the future:
- Detailed documentation
- Android Studio's plugin for generating some parts of architecture to make working with it a lot faster (architecture is designed to make it easier to write generator for it)
- Some utils extracted to libraries



