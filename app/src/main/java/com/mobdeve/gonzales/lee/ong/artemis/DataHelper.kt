package com.mobdeve.gonzales.lee.ong.artemis

import java.util.*

object DataHelper {
    fun loadPostData(): ArrayList<Post> {
        val data = ArrayList<Post>()
        val arrayTags = arrayOf("anime", "magia record", "shaft", "iroha", "witches")

        data.add(
            Post(
                R.drawable.shoob,
                "shooberino",
                R.drawable.magia_record,
                "The Doppel of Silence and Iroha Tamaki from Magia Record",
                100,
                50,
                CustomDate(2021, 7, 20),
                "Digital Art",
                1080,
                1080,
                "Its form is a cuckoo. The master of this emotion is aware of how pitiful her Doppel is, and refuses to look at it. This Doppel wordlessly wraps around and strangles anything it doesn't want to hear as it continues its search for something to fill the hole in its heart.",
                arrayTags,
                true,
                false,
                false
            )
        )
        data.add(
            Post(
                R.drawable.shibe1,
                "shibe",
                R.drawable.gorgeous_irene,
                "Gorgeous Irene",
                90,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                900,
                900,
                "Irene is a professional assassin who is able to transform into any type of woman using her 'make-up' power, but instead of killing in cold blood, she uses her abilities for good.",
                arrayTags,
                false,
                true,
                false
            )
        )
        data.add(
            Post(
                R.drawable.shibe2,
                "shoobie",
                R.drawable.faraway_jobin,
                "Faraway Jobin",
                0,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                1080,
                1080,
                "Jobin is the eccentric eldest son of the Higashikata Family. He is married with Mitsuba Higashikata and has a son, Tsurugi.",
                arrayTags,
                true,
                false,
                false
            )
        )
        data.add(
            Post(
                R.drawable.shibe3,
                "shib",
                R.drawable.moon_artwork,
                "Moon Landscape",
                69,
                42,
                CustomDate(2021, 7, 20),
                "Digital Art",
                800,
                800,
                "Picture I drew of the moon yesterday.",
                arrayTags,
                false,
                true,
                false
            )
        )
        data.add(
            Post(
                R.drawable.shibe4,
                "mameshiba",
                R.drawable.onto_the_horizon,
                "8 Bit Rat",
                1000,
                1357,
                CustomDate(2021, 7, 20),
                "Digital Art",
                678,
                1276,
                "I do commissions!",
                arrayTags,
                true,
                false,
                false
            )
        )
        return data
    }

    fun loadFollowedData(): ArrayList<Post> {
        val data = ArrayList<Post>()
        val arrayTags = arrayOf("anime", "magia record", "shaft", "iroha", "witches")

        data.add(
            Post(
                R.drawable.shibe1,
                "shibe",
                R.drawable.gorgeous_irene,
                "Gorgeous Irene",
                90,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                900,
                900,
                "Irene is a professional assassin who is able to transform into any type of woman using her 'make-up' power, but instead of killing in cold blood, she uses her abilities for good.",
                arrayTags,
                false,
                true,
                false
            )
        )
        data.add(
            Post(
                R.drawable.shibe3,
                "shib",
                R.drawable.moon_artwork,
                "Moon Landscape",
                69,
                42,
                CustomDate(2021, 7, 20),
                "Digital Art",
                800,
                800,
                "Picture I drew of the moon yesterday.",
                arrayTags,
                false,
                true,
                false
            )
        )

        return data
    }

    fun loadBookmarkData(): ArrayList<Post> {
        val data = ArrayList<Post>()
        val arrayTags = arrayOf("anime", "magia record", "shaft", "iroha", "witches")

        data.add(
            Post(
                R.drawable.shoob,
                "shooberino",
                R.drawable.magia_record,
                "The Doppel of Silence and Iroha Tamaki from Magia Record",
                100,
                50,
                CustomDate(2021, 7, 20),
                "Digital Art",
                1080,
                1080,
                "Its form is a cuckoo. The master of this emotion is aware of how pitiful her Doppel is, and refuses to look at it. This Doppel wordlessly wraps around and strangles anything it doesn't want to hear as it continues its search for something to fill the hole in its heart.",
                arrayTags,
                true,
                false,
                false
            )
        )
        data.add(
            Post(
                R.drawable.shibe2,
                "shoobie",
                R.drawable.faraway_jobin,
                "Faraway Jobin",
                0,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                1080,
                1080,
                "Jobin is the eccentric eldest son of the Higashikata Family. He is married with Mitsuba Higashikata and has a son, Tsurugi.",
                arrayTags,
                true,
                false,
                false
            )
        )
        data.add(
            Post(
                R.drawable.shibe4,
                "mameshiba",
                R.drawable.onto_the_horizon,
                "8 Bit Rat",
                1000,
                1357,
                CustomDate(2021, 7, 20),
                "Digital Art",
                678,
                1276,
                "I do commissions!",
                arrayTags,
                true,
                false,
                false
            )
        )
        return data
    }

    fun loadCommentData(): ArrayList<Comment> {
        val data = ArrayList<Comment>()

        data.add(
            Comment(
                R.drawable.shoob,
                "shooberino",
                CustomDate(2021, 7, 20),
                "Nice post, thanks for sharing"
            )
        )
        data.add(
            Comment(
                R.drawable.shibe1,
                "shibe",
                CustomDate(2021, 7, 20),
                "Kewl :D"
            )
        )
        data.add(
            Comment(
                R.drawable.shibe2,
                "shoobie",
                CustomDate(2021, 7, 20),
                "Good art"
            )
        )
        data.add(
            Comment(
                R.drawable.shibe3,
                "shib",
                CustomDate(2021, 7, 20),
                "XD"
            )
        )
        data.add(
            Comment(
                R.drawable.shibe4,
                "mameshiba",
                CustomDate(2021, 7, 20),
                "I am bean dog"
            )
        )

        return data
    }

    fun loadProfileData(): User {
        val data = User(
                "Tobe",
                "shinshibe@gmail.com",
                "shibepassword",
                R.drawable.tofu_chan,
            "Minnasan, konnichiwa! It's me, Tofu-chan!"
        )

        return data
    }

    fun loadOtherUserData(): User {
        val data = User(
            "Sample Shibe",
            "bestdoggo@gmail.com",
            "shibepassword",
            R.drawable.tofu_chan,
            "Hello, world!"
        )

        return data
    }

    fun loadHighlightsData(): ArrayList<Post> {
        val data = ArrayList<Post>()
        val arrayTags = arrayOf("anime", "magia record", "shaft", "iroha", "witches")

        data.add(
            Post(
                R.drawable.tofu_chan,
                "Tobe",
                R.drawable.surprised_gappy,
                "Josuk8",
                90,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                900,
                900,
                "Iconic low res gappy",
                arrayTags,
                false,
                true,
                true
            )
        )
        data.add(
            Post(
                R.drawable.tofu_chan,
                "Tobe",
                R.drawable.so_key_visual,
                "Stone Ocean 2021 Key Visual",
                90,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                900,
                900,
                "Almost time for stone ocean!!",
                arrayTags,
                false,
                true,
                true
            )
        )

        return data
    }

    fun loadOthersHighlightData(): ArrayList<Post> {
        val data = ArrayList<Post>()
        val arrayTags = arrayOf("anime", "magia record", "shaft", "iroha", "witches")

        data.add(
            Post(
                R.drawable.shoob,
                "shooberino",
                R.drawable.madoka_witch,
                "Walpurgisnacht",
                90,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                900,
                900,
                "Pre-Homura Madoka",
                arrayTags,
                false,
                true,
                true
            )
        )
        data.add(
            Post(
                R.drawable.shoob,
                "shooberino",
                R.drawable.the_kiss,
                "The Kiss",
                90,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                1000,
                1000,
                "Thinking of making a shibe version of this :>",
                arrayTags,
                false,
                true,
                true
            )
        )
        data.add(
            Post(
                R.drawable.shoob,
                "shooberino",
                R.drawable.small_test_pic,
                "8Bit Series Entry 1",
                90,
                10,
                CustomDate(2021, 7, 20),
                "Digital Art",
                500,
                500,
                "A smol froggo to start your day off right :D",
                arrayTags,
                false,
                true,
                true
            )
        )

        return data
    }
}