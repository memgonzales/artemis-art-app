package com.mobdeve.s14.lee.hylene.androidchallenge1

import java.util.*

class CustomDate {
    private var day_in_month: Int
    private var month: Int
    private var year: Int

    constructor() {
        val c = Calendar.getInstance()
        year = c[Calendar.YEAR]
        day_in_month = c[Calendar.DAY_OF_MONTH]
        month = c[Calendar.MONTH]
    }

    constructor(year: Int, month: Int, day_in_month: Int) {
        this.year = year
        this.day_in_month = day_in_month
        this.month = month
    }

    fun toStringFull(): String {
        return monthString[month] + " " + day_in_month + ", " + year
    }

    companion object {
        private val monthString = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
    }
}