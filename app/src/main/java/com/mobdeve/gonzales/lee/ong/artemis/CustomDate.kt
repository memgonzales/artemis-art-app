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

    // Example output if CustomDate has 2020, 10, 1: Oct 1, 2020
    fun toStringFull(): String {
        return monthString[month] + " " + day_in_month + ", " + year
    }

    // Example output if CustomDate has 2020, 10, 1: Oct 1
    fun toStringNoYear(): String {
        return monthString[month] + " " + day_in_month
    }

    companion object {
        private val monthString = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )
    }
}