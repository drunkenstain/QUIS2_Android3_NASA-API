package com.example.implement_api_publik

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var textViewTitle: TextView
    private lateinit var imageView: ImageView
    private lateinit var textViewDate: TextView
    private lateinit var textViewExplanation: TextView
    private lateinit var nasaApiService: NasaApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTitle = findViewById(R.id.textViewTitle)
        imageView = findViewById(R.id.imageView)
        textViewDate = findViewById(R.id.textViewDate)
        textViewExplanation = findViewById(R.id.textViewExplanation)

        val retrofit = RetrofitClient.getClient()
        nasaApiService = retrofit.create(NasaApiService::class.java)

        getApodData()
    }

    private fun getApodData() {
        val call = nasaApiService.getApod("fMxVZS1nJfah8ihMzGXE7N5Ik0Kx5Bw07d5nRenb")
        call.enqueue(object : Callback<NasaApodResponse> {
            override fun onResponse(call: Call<NasaApodResponse>, response: Response<NasaApodResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val apod = response.body()!!
                    textViewTitle.text = apod.title
                    textViewDate.text = apod.date
                    textViewExplanation.text = apod.explanation
                    Picasso.get().load(apod.url).into(imageView)
                } else {
                    Log.e("MainActivity", "Response unsuccessful: ${response.message()}")
                    textViewTitle.text = "Response unsuccessful"
                }
            }

            override fun onFailure(call: Call<NasaApodResponse>, t: Throwable) {
                Log.e("MainActivity", "API call failed: ${t.message}")
                textViewTitle.text = "Error: ${t.message}"
            }
        })
    }
}

