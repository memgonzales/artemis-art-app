package com.mobdeve.gonzales.lee.ong.artemis

import com.mobdeve.s14.lee.hylene.androidchallenge1.CustomDate
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
                true
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
                true
            )
        )
        data.add(
            Post(
                R.drawable.shibe3,
                "shib",
                R.drawable.surprised_gappy,
                "Early Josuk8",
                69,
                42,
                CustomDate(2021, 7, 20),
                "Digital Art",
                800,
                800,
                "Josuke is a young man afflicted with retrograde amnesia, lacking any memories prior to being discovered by Yasuho Hirose near the Wall Eyes in Morioh Town. He dedicates himself to discovering his former identity and those originally associated with him.",
                arrayTags,
                false
            )
        )
        data.add(
            Post(
                R.drawable.shibe4,
                "mameshiba",
                R.drawable.so_key_visual,
                "Stone Ocean Key Visual",
                1000,
                1357,
                CustomDate(2021, 7, 20),
                "Digital Art",
                678,
                1276,
                "Set in 2011, Florida, Jolyne Cujoh, daughter of Jotaro, is wrongfully accused of a crime she didn't commit and sent to a maximum security prison. While imprisoned, she struggles within a longstanding plot agreed between dead villain DIO and ideologue Enrico Pucci.",
                arrayTags,
                true
            )
        )
        return data
    }
}