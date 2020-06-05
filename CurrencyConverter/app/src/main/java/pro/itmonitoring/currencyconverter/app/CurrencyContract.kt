package pro.itmonitoring.currencyconverter.app

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import pro.itmonitoring.currencyconverter.Data.Model.ValCurs

interface CurrencyContract {

    interface View {
        fun showResult(result: String)
        fun showInputError()
    }

    interface Presenter {
        fun getCurrency(hasNetwork: Boolean)
        fun getCurrencyResponse()
        fun getCurrencyLocal()
        fun convert(count: String, from: String, to: String)
        fun onDestroy()
    }

    interface Repository {
        fun getDataRequest(): Disposable
        fun getDataLocal()
        fun convert(count: String, from: String, to: String): String
    }
}