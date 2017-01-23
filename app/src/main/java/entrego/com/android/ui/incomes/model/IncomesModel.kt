package entrego.com.android.ui.incomes.model

import android.os.Handler
import entrego.com.android.entity.IncomeEntity
import entrego.com.android.util.*
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.statistic.IncomesBody
import entrego.com.android.web.model.response.statistic.EntregoResultIncomes
import org.joda.time.DateTime
import org.joda.time.Days
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*


object IncomesModel {

    private const val END_POINT = "messenger/statistics/daily/revenue"

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: IncomesBody): Call<EntregoResultIncomes>
    }

    interface ResponseListener {
        fun onSuccessGetIncomes(incomes: List<IncomeEntity>)
        fun onFailureGetIncomes(code: Int?, message: String?)
    }

    fun request(token: String, range: Pair<Long, Long>, timeZone: String, listener: ResponseListener?) {


        val body = IncomesBody(range.first.formattedDate(), range.second.formattedDate(), timeZone)
        val result = LinkedHashMap<String, IncomeEntity>()
        for (i in range.first..range.second)
            result.put(i.formattedDate(), IncomeEntity(0, i.formattedDate(), 0.0))

        Logger.logd(body.toString())


        ApiCreator.server
                .create(Request::class.java)
                .parameters(token, body)
                .enqueue(object : Callback<EntregoResultIncomes> {
                    override fun onFailure(call: Call<EntregoResultIncomes>?, t: Throwable?) {
                        listener?.onFailureGetIncomes(null, t?.message)
                    }

                    override fun onResponse(call: Call<EntregoResultIncomes>?, response: Response<EntregoResultIncomes>?) {
                        Logger.logd(response?.body().toString())
                        response?.body()?.let {
                            when (it.code) {
                                0 -> {
                                    for (next in it.payload)
                                        result.put(next.day, next)
                                    listener?.onSuccessGetIncomes(result.map { it.value })
                                }
                                else -> listener?.onFailureGetIncomes(it.code, it.message)
                            }
                        }
                    }
                })
    }

}