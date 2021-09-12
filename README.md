# Artemis
**Artemis** is an **art-sharing platform** where the community can share and provide feedback on members’ works. While unregistered users are limited to viewing posted artworks and searching for posts and users, they can opt to register to maximize the features of the app. Registered users can log in to post their art and showcase some of their selected works on their profile using the Highlights feature. Moreover, they can provide their feedback on other artworks posted in the community by giving an upvote or leaving a comment. 

Users also have the option to bookmark their favorite artworks made by other users so that they can view or return to them at a later time. Additionally, they can share posts on their Facebook accounts through its application programming interface (API). Users can search for a post by means of tags (e.g., “3D,” “watercolor,” and “pixel art”) and follow other users to see their posts in a dedicated section on their feed.

***The source code documentation can be accessed through the following website: https://memgonzales.github.io/artemis-art-app/.***

## Task
**Artemis** is the major course output in a mobile development class. The students are tasked to create an Android app of their choice, provided that it satisfied the minimum requirement of being connected to a remote server or featuring at least two of the following services: telephony, local database, geolocation, touch gestures that are non-native to the views used, camera usage, canvas or graphics-related applications, motion sensors, service that runs on the background, and usage of third-party APIs.

The application development is divided into two phases:

Phase | Duration | Description
-- | -- | --
1 (Beta Demo) | 2 weeks | Completion of front-end views or layouts for all the activities, and implementation of either at least one API feature or all the database helpers (however, only the schemas are needed for apps that use a remote database)
2 (Final Demo) | 3 weeks | Completion of the entire Android app (i.e., the implementation of all the features enumerated in the proposed project specifications)

## Running the Application
***The minimum SDK supported is Android Lollipop (API Level 21), and the target SDK is Android 11 (API Level 30).***

1. Before running the application locally, the following software are recommended (albeit not required) to be installed:

   | Software | Description | Download Link | License |
   | --- | --- | --- | --- |
   | git | Distributed version control system | https://git-scm.com/downloads | GNU General Public License v2.0 |
   
2. Create a copy of this repository:
   - If git is installed, type the following command on the terminal:
   
     ```
     git clone https://github.com/memgonzales/linquiztics
     ```
      
   - If git is not installed, click the green 'Code' button near the top right of the repository and choose 'Download ZIP'. Once the zipped folder has been downloaded, extract its contents.

3. Run the app using Android Studio (or any other IDE that supports Android development). 

   If your device does not have any IDE installed, 

## Dependencies
This project uses the following project dependencies:

Dependency | Version | License
-- | -- | --
`com.android.tools.build:gradle` | 4.2.1 | Apache License 2.0
`org.jetbrains.kotlin:kotlin-gradle-plugin` | 1.5.0 | Apache License 2.0
`org.jetbrains.dokka:dokka-gradle-plugin` | 1.5.0 | Apache License 2.0
`com.google.gms:google-services` | 4.3.10 | Apache License 2.0

It also uses the following app dependencies:

Dependency | Version | License
-- | -- | --
`org.jetbrains.kotlin:kotlin-stdlib` | 1.5.0 | Apache License 2.0
`androidx.core:core-ktx` | 1.5.0 | Apache License 2.0
`androidx.appcompat:appcompat` | 1.3.1. | Apache License 2.0
`com.google.android.material:material` | 1.4.0 | Apache License 2.0
`androidx.constraintlayout:constraintlayout` | 2.0.4 | Apache License 2.0
`androidx.coordinatorlayout:coordinatorlayout` | 1.1.0 | Apache License 2.0
`com.facebook.shimmer:shimmer` | 0.5.0 | BSD License
`de.hdodenhof:circleimageview` | 3.1.0 | Apache License 2.0
`com.google.android.material:material` | 1.2.1 | Apache License 2.0
`androidx.legacy:legacy-support-v4` | 1.0.0 | Apache License 2.0
`com.google.firebase:firebase-auth-ktx` | 21.0.1 | Apache License 2.0
`com.google.firebase:firebase-database-ktx` | 20.0.1 | Apache License 2.0
`com.google.firebase:firebase-config-ktx` | 21.0.1 | Apache License 2.0
`com.google.firebase:firebase-storage-ktx` | 20.0.0 | Apache License 2.0
`com.facebook.android:facebook-android-sdk` | \[5, 6) | Facebook Platform License
`com.github.bumptech.glide:glide` | 4.12.0 |BSD License <br/> MIT License (part) <br/> Apache License 2.0
`junit:junit` | 4.+ | Eclipse Public License 1.0
`androidx.test.ext:junit` | 1.1.3 | Eclipse Public License 1.0 <br/> Apache License 2.0
`androidx.test.espresso:espresso-core` | 3.4.0 | Apache License 2.0
`androidx.recyclerview:recyclerview` | 1.2.1 | Apache License 2.0

## Built Using
This project uses the following languages and technologies:
- **Logic**: <a href = "https://kotlinlang.org/">Kotlin</a>, a statisically-typed language officially endorsed by Google as the preferred language for Android development
- **Layouts**: <a href = "https://developer.android.com/guide/topics/ui/declaring-layout">XML</a>, a lightweight markup language that is both human- and machine-readable
- **Database**: <a href = "https://firebase.google.com/docs/database">Firebase Database</a>, a cloud-based NoSQL database featuring real-time data synchronization

The <a href = "https://memgonzales.github.io/artemis-art-app/">HTML documentation</a> of the source code was generated from <a href = "https://kotlinlang.org/docs/kotlin-doc.html">KDoc</a> comments via the documentation engine <a href = "https://github.com/Kotlin/dokka">Dokka</a>.

## Authors
- <b>Mark Edward M. Gonzales</b> <br/>
  mark_gonzales@dlsu.edu.ph <br/>
  gonzales.markedward@gmail.com <br/>
  
- <b>Hylene Jules G. Lee</b> <br/>
  hylene_jules_lee@dlsu.edu.ph <br/>
  lee.hylene@gmail.com
  
- <b>Phoebe Clare L. Ong</b> <br/>
  phoebs_ong@dlsu.edu.ph

Assets (images and XML-based layouts) are properties of their respective owners. Attribution is found in the file `credits.txt`.
