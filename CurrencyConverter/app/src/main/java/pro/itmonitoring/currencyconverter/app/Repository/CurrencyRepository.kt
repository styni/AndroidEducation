package pro.itmonitoring.currencyconverter.app.Repository

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pro.itmonitoring.currencyconverter.app.CurrencyContract
import pro.itmonitoring.currencyconverter.Data.Preferences.PreferencesHelper
import pro.itmonitoring.currencyconverter.Data.Model.ValCurs
import pro.itmonitoring.currencyconverter.Data.Request.RequestClient
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyRepository(private var preferences: PreferencesHelper) : CurrencyContract.Repository {

    var rates: Map<String, Double>? = null


    @SuppressLint("CheckResult")
    override fun getDataRequest() {
        val request = RequestClient
            .create()
            .getCurrency()

        request
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result ->
                rates = valToMap(result)
                preferences.saveCurrency(rates!!)
                Log.d("CURR_RES", result.toString())
            }, { error ->
                Log.d("ERRORS", error.message)
            })

    }

    fun valToMap(valCur : ValCurs): Map<String, Double> {
        val rates: MutableMap<String, Double> = mutableMapOf()
        for(i in valCur.list!!) {
            rates[i.CharCode!!] = i.Value!!.replace(",",".").toDouble()
        }

        return rates
    }

    override fun getDataLocal() {
        rates = preferences.loadCurrency()
    }

    override fun convert(count: String, from: String, to: String): String {
        var result = 0.0
        val count = count.toDouble()
        val mFrom = rates?.get(from)
        val mTo = rates?.get(to)

        result = if (from == to ) {
            count
        } else if (from == "RUR") {
            count * mTo!!
        } else if (to == "RUR") {
            count / mFrom!!
        } else {
            count / mFrom!! * mTo!!
        }

        result = BigDecimal(result).setScale(3, RoundingMode.UP).toDouble()

        return result.toString()
    }
}