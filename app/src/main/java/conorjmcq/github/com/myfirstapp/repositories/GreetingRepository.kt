package conorjmcq.github.com.myfirstapp.repositories

import android.content.Context
import android.widget.Toast
import conorjmcq.github.com.myfirstapp.models.GreetingResponse
import conorjmcq.github.com.myfirstapp.services.GreetingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GreetingRepository {
    private val greetingService: GreetingService? = null
    // ...
    fun getGreeting(userName: String, context: Context) {
        greetingService!!.greeting(userName)
    }
}