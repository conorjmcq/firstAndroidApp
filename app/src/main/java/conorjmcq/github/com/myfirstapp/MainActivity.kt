package conorjmcq.github.com.firstAndroidApp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun toastMe(@Suppress("UNUSED_PARAMETER")view: View) {
        // Get the text view
        val countString = findViewById<TextView>(R.id.textView).text.toString()

        // val myToast = Toast.makeText(this, message, duration);
        val myToast = Toast.makeText(this, """Count is $countString""", Toast.LENGTH_SHORT)
        myToast.show()
    }

    fun countMe (@Suppress("UNUSED_PARAMETER")view: View) {
        // Get the text view
        val showCountTextView = findViewById<TextView>(R.id.textView)

        // Get the value of the text view.
        val countString = showCountTextView.text.toString()

        // Convert value to a number and increment it
        var count: Int = Integer.parseInt(countString)
        count++

        // Display the new value in the text view.
        showCountTextView.text = count.toString()
    }

    fun randomMe (@Suppress("UNUSED_PARAMETER")view: View) {
        // Get the value of the text view
        val count = Integer.parseInt(findViewById<TextView>(R.id.textView).text.toString())

        // Create an Intent to start the second activity
        val randomIntent = Intent(this, SecondActivity::class.java)

        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, count)

        // Start the new activity.
        startActivity(randomIntent)
    }
}
