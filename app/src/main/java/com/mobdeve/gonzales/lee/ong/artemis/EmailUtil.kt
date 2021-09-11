package com.mobdeve.gonzales.lee.ong.artemis

/**
 * Class containing the email address, subject lines, suggested contents of the message body, and
 * titles of the mail client choosers should the user send an email to the app developers.
 *
 * @constructor Creates a class that contains the email address, subject lines, suggested contents
 * of the message body, and titles of the mail client users should the user send an email to the
 * app developers.
 */
class EmailUtil {
    /**
     * Companion object containing the email address, subject lines, suggested contents
     * of the message body, and titles of the mail client users should the user send an email
     * to the app developers.
     */
    companion object {
        /**
         * Email address of the app developers.
         */
        const val EMAIL_ADDRESS = "artemis.appmaster@gmail.com"

        /**
         * Subject line in relation to the user reporting persistent redirection to the
         * broken link activity.
         */
        const val SUBJECT_BROKEN_LINK = "[User Report] Persistent Broken Link"

        /**
         * Suggested contents of the message body in relation to the user reporting persistent
         * redirection to the broken link activity.
         */
        const val TEXT_BROKEN_LINK = "Kindly include your username and describe what you were doing when this error occurred"

        /**
         * Title of the mail client chooser in relation to the user reporting persistent
         * redirection to the broken link activity.
         */
        const val TITLE_BROKEN_LINK = "Send a report"

        /**
         * Subject line in relation to the user sending a feedback or inquiry.
         */
        const val SUBJECT_FEEDBACK = "User Feedback/Inquiry"

        /**
         * Suggested contents of the message body in relation to the user sending a feedback or inquiry.
         */
        const val TEXT_FEEDBACK = "Kindly place your feedback or inquiries related to Artemis"

        /**
         * Title of the mail client chooser in relation to the user sending a feedback or inquiry.
         */
        const val TITLE_FEEDBACK = "Send feedback or inquiry"
    }
}