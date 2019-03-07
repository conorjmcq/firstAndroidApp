package conorjmcq.github.com.myfirstapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRequest {
    val retrofitBuild: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.157.196.117:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}