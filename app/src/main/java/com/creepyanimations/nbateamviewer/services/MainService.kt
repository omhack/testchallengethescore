package com.creepyanimations.nbateamviewer.services

import android.content.Context
import com.creepyanimations.nbateamviewer.models.Team
import com.creepyanimations.nbateamviewer.shared.NBA_TEAM_ENDPOINT
import com.creepyanimations.nbateamviewer.shared.hasNetworkAvailable
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

class RetrofitService(context: Context) {
    private var retrofit: Retrofit
    private val srvc: TeamsNetworkService

    val cacheSize = (5 * 1024 * 1024).toLong() //we define a 5mb cache
    val myCache = Cache(context.cacheDir, cacheSize)

    init {
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetworkAvailable(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
        val client = builder.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(NBA_TEAM_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        srvc = retrofit.create(TeamsNetworkService::class.java)
    }

    fun fetchTeams(id : String): Single<List<Team>> {
        return Single.create { emitter ->
            val teamsCall = srvc.getTeams(id)
            teamsCall.enqueue(object : RetrofitCallback<List<Team>> {

                override fun onResponse(call: Call<List<Team>>, response: Response<List<Team>>) {
                    if (response.isSuccessful) {
                        val respond = response.body()

                        respond.let {
                            val teams = respond!!

                            emitter.onSuccess(teams)
                        }

                    } else {
                        emitter.onError(Exception("Failed to fetch the book information"))
                    }

                }

                override fun onFailure(call: Call<List<Team>>, t: Throwable) {
                    emitter.onError(t)
                }

            })
        }
    }

}



interface RetrofitCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>)

    override fun onFailure(call: Call<T>, t: Throwable)
}

interface TeamsService {

    /** Gets the list of Teams from NBA **/
    fun getTeams(id: String, context: Context): Single<List<Team>>
}


interface TeamsNetworkService {

    @GET("bins/{id}")
    fun getTeams(@Path("id") isbn: String): Call<List<Team>>

}
