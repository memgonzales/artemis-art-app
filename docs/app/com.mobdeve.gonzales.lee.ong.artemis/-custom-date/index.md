//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[CustomDate](index.md)

# CustomDate

[androidJvm]\
class [CustomDate](index.md)

Class representing a custom-formatted date.

This class is a direct Kotlin translation of the Java code given by Ms. Candy Joyce H. Espulgar (De La Salle University, Mobile Development - MOBDEVE, Term 3, Academic Year 2020-2021)

## Constructors

| | |
|---|---|
| [CustomDate](-custom-date.md) | [androidJvm]<br>fun [CustomDate](-custom-date.md)()<br>Creates a custom-formatted date based on the current date. |
| [CustomDate](-custom-date.md) | [androidJvm]<br>fun [CustomDate](-custom-date.md)(year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), month: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), dayInMonth: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Creates a custom-formatted date given the year, month, and day components. |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md)<br>Companion object containing an array that lists the unabbreviated names of the months in the order in which they appear in the Gregorian calendar. |

## Properties

| Name | Summary |
|---|---|
| [dayInMonth](day-in-month.md) | [androidJvm]<br>private var [dayInMonth](day-in-month.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Day component of the date. |
| [month](month.md) | [androidJvm]<br>private var [month](month.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Month component of the date. |
| [year](year.md) | [androidJvm]<br>private var [year](year.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Year component of the date. |

## Functions

| Name | Summary |
|---|---|
| [toStringFull](to-string-full.md) | [androidJvm]<br>fun [toStringFull](to-string-full.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Returns a string representing the date in the following format: Month DD, YYYY (where Month is the unabbreviated name of the month). |
