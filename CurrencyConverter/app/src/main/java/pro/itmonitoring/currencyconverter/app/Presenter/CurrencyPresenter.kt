package pro.itmonitoring.currencyconverter.app.Presenter

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import io.reactivex.disposables.CompositeDisposable
import pro.itmonitoring.currencyconverter.Data.Model.ValCurs
import pro.itmonitoring.currencyconverter.app.CurrencyContract
import java.io.IOException
import java.net.MalformedURLException
import java.util.*

class CurrencyPresenter (
    private val view: CurrencyContract.View,
    private val repository: CurrencyContract.Repository) : CurrencyContract.Presenter {
    private val disposable = CompositeDisposable()

    override fun getCurrency(hasNetwork: Boolean) {
        if (hasNetwork) {
            getCurrencyResponse()
        } else {
            getCurrencyLocal()
        }
    }

    override fun getCurrencyResponse() {
        disposable.add(repository.getDataRequest())
    }

    override fun getCurrencyLocal() {
        repository.getDataLocal()
    }

    override fun convert(count: String, from: String, to: String) {
        if (TextUtils.isEmpty(count)) {
            view.showInputError()
            return
        }

        val result: String = repository.convert(count, from, to)
        view.showResult(result)
    }

    override fun onDestroy() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }


}