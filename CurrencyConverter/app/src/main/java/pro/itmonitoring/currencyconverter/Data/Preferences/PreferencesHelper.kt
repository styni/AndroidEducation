package pro.itmonitoring.currencyconverter.Data.Preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferencesHelper(private val context: Context) {
    val KEY_CUR: String = "currency"

    private var sPref: SharedPreferences =
        context.getSharedPreferences("save_cur", Activity.MODE_PRIVATE)

    private var editor: SharedPreferences.Editor = sPref.edit()

    fun saveCurrency(cur: Map<String, Double>) {
        var set: Set<String> = mutableSetOf()
        for(i in cur) {
            set.plus("${i.key}:${i.value}")
        }
        editor.putStringSet(KEY_CUR, set)
        editor.apply()
    }

    fun loadCurrency(): MutableMap<String, Double>? {
        val cur: Set<String>? = sPref.getStringSet(KEY_CUR, null)
        val map: MutableMap<String, Double>? = mutableMapOf()
        if (cur != null) {
            for (i in cur) {
                var temp = i.split(":")
                Log.d("DFDF", temp[0] + temp[1])
                map?.set(temp[0], temp[1].toDouble())
            }
            return map
        }
        return null
    }
}