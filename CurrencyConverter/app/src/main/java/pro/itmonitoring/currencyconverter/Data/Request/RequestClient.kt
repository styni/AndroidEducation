package pro.itmonitoring.currencyconverter.Data.Request

import io.reactivex.Observable
import pro.itmonitoring.currencyconverter.Data.Model.ValCurs
import pro.itmonitoring.currencyconverter.Util.Const
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

interface RequestClient {
    @GET(Const.endPoint)
    fun getCurrency(): Observable<ValCurs>

    companion object Factory {
        fun create() : RequestClient {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(Const.URL)
                .build()
            return retrofit.create(RequestClient::class.java)

        }
    }


}