package com.mobdeve.gonzales.lee.ong.artemis

/**
 * Class containing the durations (in milliseconds) of the animations in the layouts.
 *
 * @constructor Creates a class that contains the durations (in milliseconds) of the animations
 * in the layouts.
 */
class AnimationDuration {
    /**
     * Companion object containing the durations (in milliseconds) of the animations in the layouts.
     */
    companion object {
        /**
         * Duration of the display of the splash screen.
         *
         * This should be set to a value less than 3000 ms, per the recommendation of Campbell
         * (2018): https://uxdesign.cc/building-the-perfect-splash-screen-46e080395f06.
         */
        const val SPLASH_SCREEN_TIMEOUT = 2600

        /**
         * Duration of the display of the fadeout of the first iteration of the logo frame animation
         * in the splash screen.
         */
        const val ANIMATION_FRAME_FADEOUT = 400

        /**
         * Mock duration of the display of the shimmer layout (overridden by the time needed
         * to complete the asynchronous fetching of data from the Firebase server).
         */
        const val SHIMMER_TIMEOUT = 2000

        /**
         * Mock duration of the display of refresh icon when the screen is swiped (overridden by
         * the time needed to complete the asynchronous fetching of data from the Firebase server).
         */
        const val REFRESH_TIMEOUT = 1500

        /**
         * Waiting time for data fetching before the display of the notice indicating that the
         * activity has no posts (for example, in the feed or highlights).
         */
        const val NO_POST_TIMEOUT = 2000
    }
}