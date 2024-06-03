import goryachkovskiy.danil.mtuci.kmm.repository.Preferences
import java.io.File
import java.util.*

class DesktopPreferences : Preferences {
    private val properties = Properties()
    private val preferencesFile = File("preferences.properties")

    init {
        if (preferencesFile.exists()) {
            preferencesFile.inputStream().use { properties.load(it) }
        }
    }

    override fun setBoolean(key: String, value: Boolean) {
        properties.setProperty(key, value.toString())
        preferencesFile.outputStream().use { properties.store(it, null) }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return properties.getProperty(key, defaultValue.toString()).toBoolean()
    }
}