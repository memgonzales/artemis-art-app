package com.mobdeve.gonzales.lee.ong.artemis

import java.util.*

/**
 * Class representing a custom-formatted date.
 *
 * This class is a direct Kotlin translation of the Java code given by Ms. Candy Joyce H. Espulgar
 * (De La Salle University, Mobile Development - MOBDEVE, Term 3, Academic Year 2020-2021)
 *
 * @constructor Creates a class representing a custom-formatted date.
 */
class CustomDate {
    /**
     * Day component of the date.
     */
    private var dayInMonth: Int

    /**
     * Month component of the date.
     */
    private var month: Int

    /**
     * Year component of the date.
     */
    private var year: Int

    /**
     * Creates a custom-formatted date based on the current date.
     */
    constructor() {
        val c = Calendar.getInstance()
        year = c[Calendar.YEAR]
        dayInMonth = c[Calendar.DAY_OF_MONTH]
        month = c[Calendar.MONTH]
    }

    /**
     * Creates a custom-formatted date given the year, month, and day components.
     *
     * @param year year component of the date
     * @param month month component of the date
     * @param dayInMonth day component of the date
     */
    constructor(year: Int, month: Int, dayInMonth: Int) {
        this.year = year
        this.dayInMonth = dayInMonth
        this.month = month
    }

    /**
     * Returns a string representing the date in the following format: Month DD, YYYY (where Month
     * is the unabbreviated name of the month).
     *
     * @return date in the following format: Month DD, YYYY (where Month is the unabbreviated name
     * of the month).
     */
    fun toStringFull(): String {
        return monthString[month] + " " + dayInMonth + ", " + year
    }

    /**
     * Companion object containing an array that lists the unabbreviated names of the months
     * in the order in which they appear in the Gregorian calendar.
     */
    companion object {
        /**
         * Array listing the unabbreviated names of the months in the order in which they appear
         * in the Gregorian calendar.
         */
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