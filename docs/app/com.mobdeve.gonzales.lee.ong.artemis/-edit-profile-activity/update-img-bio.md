//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[EditProfileActivity](index.md)/[updateImgBio](update-img-bio.md)

# updateImgBio

[androidJvm]\
private fun [updateImgBio](update-img-bio.md)(bio: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Saves the new profile picture and short bio in the remote database, effectively replacing the old profile picture and short bio of the user.

If the user choose to remove their current profile picture, a placeholder photo is saved instead (currently, this placeholder is the launcher icon of the app, that is, a chibi version of the Artemis logo).

## Parameters

androidJvm

| | |
|---|---|
| bio | new short bio of the user |
