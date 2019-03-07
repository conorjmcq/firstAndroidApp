package conorjmcq.github.com.firstAndroidApp

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import co.poynt.os.model.Intents
import co.poynt.os.model.PoyntError
import co.poynt.os.services.v1.IPoyntProductListener
import co.poynt.os.services.v1.IPoyntProductService
import conorjmcq.github.com.myfirstapp.ProductsAdapter
import conorjmcq.github.com.myfirstapp.models.GreetingResponse
import conorjmcq.github.com.myfirstapp.models.Product
import conorjmcq.github.com.myfirstapp.services.GreetingService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private var mProductService: IPoyntProductService? = null

    private var  productList: ArrayList<Product> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter

    private val productServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mProductService = IPoyntProductService.Stub.asInterface(service)
            Log.d(TAG, "productService connected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mProductService = null
            Log.d(TAG, "productService disconnected")
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intents.getComponentIntent(Intents.COMPONENT_POYNT_PRODUCT_SERVICE),
            productServiceConnection, Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()
        unbindService(productServiceConnection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<View>(R.id.barcode_input) as EditText
        editText.setOnEditorActionListener { textView, action, event -> handleScan(textView, event) }
        editText.requestFocus()

        addPredefinedProducts()

        recyclerView = findViewById<View>(R.id.product_list_view) as RecyclerView

        productsAdapter = ProductsAdapter(productList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productsAdapter

    }

    object KeyupEditorObject: OnEditorActionListener {
        override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
            if(event.action == KeyEvent.ACTION_UP) {
                Log.d("ACTION_GO", "KEY ENTER RECORDED")
                v.text = ""
            }
            return true
        }
    }

    private fun handleScan(textView: TextView, event: KeyEvent): Boolean {
        if(event.action == KeyEvent.ACTION_UP) {
            Log.d("ACTION_GO", "KEY ENTER RECORDED")
            val upc = textView.text
            textView.text = ""

            mProductService?.getProductByUpc(upc.toString(), mProdCatWithProdListener)

        }
        return true
    }

    private val mProdCatWithProdListener = object : IPoyntProductListener.Stub() {
        override fun onResponse(p0: co.poynt.api.model.Product?, p1: PoyntError?) {
            Log.d(TAG, p0?.name)
        }
    }

    private fun addPredefinedProducts() {
        productList.add(Product("Mars Bar", 0))
        productList.add(Product("Adolfo", 1))
        productList.add(Product("Snickers Bar", 2))
    }

    fun toastMe(@Suppress("UNUSED_PARAMETER")view: View){

        val client = OkHttpClient()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.157.196.117:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val greetingService = retrofit.create(GreetingService::class.java)

        val call = greetingService.greeting("PoyntUser1").enqueue(object : Callback<GreetingResponse> {
            override fun onResponse(call: Call<GreetingResponse>, response: Response<GreetingResponse>) {
                val myToast = Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_SHORT)
                myToast.show()
            }

            override fun onFailure(call: Call<GreetingResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
