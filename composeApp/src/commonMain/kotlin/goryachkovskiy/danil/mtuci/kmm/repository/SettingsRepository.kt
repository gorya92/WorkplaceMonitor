package goryachkovskiy.danil.mtuci.kmm.repository

interface Preferences {
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
}

class UserSession(private val preferences: Preferences) {
    private val IS_LOGGED_IN_KEY = "is_logged_in"

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(IS_LOGGED_IN_KEY, false)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        preferences.setBoolean(IS_LOGGED_IN_KEY, loggedIn)
    }
}