package conorjmcq.github.com.myfirstapp.services

import conorjmcq.github.com.myfirstapp.models.GreetingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GreetingService {
    /**
     * @GET declares an HTTP GET request
     * @Query("name") annotation on the userName parameter adds it as a url
     * query in the form /greeting?name={userName}
     */
    @GET("/greeting")
    fun greeting(@Query("name") userName: String): Call<GreetingResponse>
}