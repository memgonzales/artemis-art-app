# Artemis
![badge][badge-kotlin]
![badge][badge-android] 
![badge][badge-gradle]
![badge][badge-fb]
![badge][badge-firebase]

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

## Project Structure
The project consists of the following folders:

Folder | Description
-- | --
<a href = "https://github.com/memgonzales/artemis-art-app/tree/master/.idea">`.idea`</a> | Contains files used by Android Studio to load project-specific configurations
<a href = "https://github.com/memgonzales/artemis-art-app/tree/master/app">`app`</a> | Contains all the source code and resource files, the markdown file for the app- and package-level documentation, and files defining the module-specific dependencies, project-specific ProGuard rules, and developer credentials related to Google services
<a href = "https://github.com/memgonzales/artemis-art-app/tree/master/docs">`docs`</a> | Contains the HTML documentation of the project, generated from KDoc comments via Dokka
<a href = "https://github.com/memgonzales/artemis-art-app/tree/master/gradle/wrapper">`gradle`</a> | Contains files used by Gradle to run build automation tasks

Several Gradle-related files are also included in its root directory.

The complete project specifications can be found in the document <a href = "https://github.com/memgonzales/artemis-art-app/blob/master/Proposed%20Specifications.pdf">`Project Specifications.pdf`</a>.


## Running the Application
***The minimum SDK supported is Android Lollipop (API Level 21), and the target SDK is Android 11 (API Level 30).***

1. Before running the application locally, the following software are recommended (albeit not required) to be installed:

   | Software | Description | License |
   | --- | --- | --- |
   | <a href = "https://git-scm.com/downloads">git</a> | Distributed version control system | GNU General Public License v2.0 |
   | <a href = "https://developer.android.com/studio">Android Studio</a> | Official integrated development environment (IDE) for Android development | Apache License 2.0
   
2. Create a copy of this repository:
   - If git is installed, type the following command on the terminal:
   
     ```
     git clone https://github.com/memgonzales/artemis-art-app
     ```
      
   - If git is not installed, click the green <code>Code</code> button near the top right of the repository and choose <code>Download ZIP</code>. Once the zipped folder has been downloaded, extract its contents.

3. Run the app using Android Studio (or any IDE that supports Android development). Alternatively, Android also provides a <a href = "https://developer.android.com/studio/build/building-cmdline">guide</a> on how to build the app from the command line.

<img src="https://github.com/memgonzales/artemis-art-app/blob/master/screenshots/screenshot-2.jpg?raw=True" alt="Screenshot 2" width = 750> 
<img src="https://github.com/memgonzales/artemis-art-app/blob/master/screenshots/screenshot-1.jpg?raw=True" alt="Screenshot 1" width = 750> 

### Credentials (For Testing Only)
To test the Facebook sharing feature, log into Facebook using the following credentials:

- **Email Address:** `tester.artemis@gmail.com`
- **Password:** `ASDFGHJKL123;`

*Using this tester account is necessary since the project has yet to undergo Facebook's [App Review](https://developers.facebook.com/docs/app-review/). Alternatively, send a Facebook friend request to [Artemis Art App](https://www.facebook.com/profile.php?id=100072056787027) to be added as a tester.* 


## Dependencies
This project uses the following project dependencies:

Dependency | Version | Description | License
-- | -- | -- | --
`com.android.tools.build:gradle` | 4.2.1 | Gradle build automation tool | Apache License 2.0
`org.jetbrains.kotlin:kotlin-gradle-plugin` | 1.5.0 | Gradle plugin for Kotlin/JVM compilation tasks | Apache License 2.0
`org.jetbrains.dokka:dokka-gradle-plugin` | 1.5.0 | Gradle plugin for Dokka documentation engine | Apache License 2.0
`com.google.gms:google-services` | 4.3.10 | Plugin for processing the <a href = "https://github.com/memgonzales/artemis-art-app/blob/master/app/google-services.json">`google-servies.json`</a> file | Apache License 2.0

It also uses the following module-specific dependencies:

Dependency | Version | Description | License
-- | -- | -- | --
`org.jetbrains.kotlin:kotlin-stdlib` | 1.5.0 | Kotlin standard library for JVM | Apache License 2.0
`androidx.core:core-ktx` | 1.5.0 | Core module providing Kotlin extensions for common framework APIs and several domain-specific extensions | Apache License 2.0
`androidx.appcompat:appcompat` | 1.3.1. | Library allowing access to new APIs on older API versions of the platform | Apache License 2.0
`com.google.android.material:material` | 1.2.1 <br/> 1.4.0 | Library for using APIs that provide implementations of the Material Design specification | Apache License 2.0
`androidx.constraintlayout:constraintlayout` | 2.0.4 | Library for positioning and sizing widgets in a flexible way with relative positioning | Apache License 2.0
`androidx.coordinatorlayout:coordinatorlayout` | 1.1.0 | Library for positioning top-level application widgets | Apache License 2.0
`com.facebook.shimmer:shimmer` | 0.5.0 | Library for adding a shimmer layout as an unobtrusive loading indicator | BSD License
`de.hdodenhof:circleimageview` | 3.1.0 | Library providing a circular ImageView | Apache License 2.0
`androidx.legacy:legacy-support-v4` | 1.0.0 | Library for using APIs that are either not available for older platform versions or not part of the framework APIs | Apache License 2.0
`com.google.firebase:firebase-auth-ktx` | 21.0.1 | Firebase authentication for Kotlin | Apache License 2.0
`com.google.firebase:firebase-database-ktx` | 20.0.1 | Firebase database for Kotlin | Apache License 2.0
`com.google.firebase:firebase-config-ktx` | 21.0.1 | Firebase remote configuration for Kotlin | Apache License 2.0
`com.google.firebase:firebase-storage-ktx` | 20.0.0 | Firebase storage for Kotlin | Apache License 2.0
`com.facebook.android:facebook-android-sdk` | \[5, 6) | Facebook software development kit for Android | Facebook Platform License
`com.github.bumptech.glide:glide` | 4.12.0 | Efficient media management and image loading framework that wraps media decoding, memory and disk caching, and resource pooling  | BSD License <br/> MIT License (part) <br/> Apache License 2.0
`junit:junit` | 4.+ | Unit testing framework for Java | Eclipse Public License 1.0
`androidx.test.ext:junit` | 1.1.3 | AndroidX unit testing framework for Java | Eclipse Public License 1.0 <br/> Apache License 2.0
`androidx.test.espresso:espresso-core` | 3.4.0 | Framework for writing Android user interface tests | Apache License 2.0
`androidx.recyclerview:recyclerview` | 1.2.1 | Library for displaying large sets of data in your user interface while minimizing memory usage | Apache License 2.0
`at.favre.lib:bcrypt` | 0.9.0 | Implementation of the OpenBSD Blowfish password hashing algorithm, based on jBcrypt | Apache License 2.0

*The descriptions are taken from their respective websites.*

## Built Using
This project uses the following languages and technologies:
- **Logic**: <a href = "https://kotlinlang.org/">Kotlin</a>, a statisically-typed language officially endorsed by Google as the preferred language for Android development
- **Layouts**: <a href = "https://developer.android.com/guide/topics/ui/declaring-layout">XML</a>, a lightweight markup language that is both human- and machine-readable
- **Database**: <a href = "https://firebase.google.com/docs/database">Firebase Database</a>, a cloud-based NoSQL database featuring real-time data synchronization

The <a href = "https://memgonzales.github.io/artemis-art-app/">HTML documentation</a> of the source code was generated from <a href = "https://kotlinlang.org/docs/kotlin-doc.html">KDoc</a> comments via the documentation engine <a href = "https://github.com/Kotlin/dokka">Dokka</a>.

Password encryption is implemented using [bcrypt](https://github.com/patrickfav/bcrypt) and [scrypt](https://firebaseopensource.com/projects/firebase/scrypt/).

## Authors
- <b>Mark Edward M. Gonzales</b> <br/>
  mark_gonzales@dlsu.edu.ph <br/>
  gonzales.markedward@gmail.com <br/>
  
- <b>Hylene Jules G. Lee</b> <br/>
  hylene_jules_lee@dlsu.edu.ph <br/>
  lee.hylene@gmail.com
  
- <b>Phoebe Clare L. Ong</b> <br/>
  phoebs_ong@dlsu.edu.ph <br/>
  ongphoebeclare@gmail.com
  
For queries or concerns related to the Artemis project, kindly email artemis.appmaster@gmail.com.

Assets (images and XML-based layouts) are properties of their respective owners. Attribution is found in the file <a href = "https://github.com/memgonzales/artemis-art-app/blob/master/credits.txt">`credits.txt`</a>.

[badge-android]: http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat
[badge-kotlin]: https://img.shields.io/badge/kotlin-%230095D5.svg?&style=flat&logo=kotlin&logoColor=white
[badge-gradle]: https://img.shields.io/badge/Gradle-02303A.svg?style=flate&logo=Gradle&logoColor=white
[badge-fb]: https://img.shields.io/badge/Facebook-1877F2?style=flat&logo=facebook&logoColor=white
[badge-firebase]: https://img.shields.io/badge/firebase-%23039BE5.svg?style=flat&logo=firebase
