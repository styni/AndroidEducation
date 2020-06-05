package pro.itmonitoring.currencyconverter.app.View

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import pro.itmonitoring.currencyconverter.R
import pro.itmonitoring.currencyconverter.app.CurrencyContract
import pro.itmonitoring.currencyconverter.app.Presenter.CurrencyPresenter
import pro.itmonitoring.currencyconverter.app.Repository.CurrencyRepository
import pro.itmonitoring.currencyconverter.Data.Preferences.PreferencesHelper

class MainActivity : AppCompatActivity(), CurrencyContract.View {

    private lateinit var presenter: CurrencyPresenter
    private lateinit var preferences: PreferencesHelper
    private lateinit var repository: CurrencyRepository
    private val COUNT_KEY: String = "count"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = PreferencesHelper(context = this)
        repository = CurrencyRepository(preferences)

        presenter = CurrencyPresenter(view = this, repository = repository)
        presenter.getCurrency(isNetworkAvailable())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (et_output_currency.text.isNotEmpty()) {
            outState.putString(COUNT_KEY, et_output_currency.text.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        et_output_currency.text = savedInstanceState.getString(COUNT_KEY)
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun clickConvert(view: View) {
        val count = et_input_currency.text.toString().trim()
        val from = s_input_currency.selectedItem.toString()
        val to = s_output_currency.selectedItem.toString()

        presenter.convert(count, from, to)
    }

    override fun showResult(result: String) {
        et_output_currency.text = result
    }

    override fun showInputError() {
        Toast.makeText(this, getString(R.string.field_is_empty), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}
