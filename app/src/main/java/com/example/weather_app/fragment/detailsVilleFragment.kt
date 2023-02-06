package com.example.weather_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.weather_app.R
import com.example.weather_app.Weather
import com.example.weather_app.WeatherService
import com.example.weather_app.model.Ville
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detailsVilleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsVilleFragment : Fragment() {


    private lateinit var nomVille: TextView
    private lateinit var mainTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var tempTextView: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var pressureTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var speedTextView: TextView
    private lateinit var temp_minTextView: TextView
    private lateinit var temp_maxTextView: TextView



    private lateinit var cityName: String
    private val API_KEY = "bee682fc84b90f3d40226f949cb59b06"



    fun DetailVilleFragment() {
        // Required empty public constructor
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_ville, container, false)


        val ville = arguments?.getSerializable("ville") as Ville
         cityName = ville.name

         nomVille = view.findViewById<TextView>(R.id.nomVille)
         nomVille.text = cityName
         mainTextView = view.findViewById<TextView>(R.id.main_text_view)
         descriptionTextView = view.findViewById<TextView>(R.id.description_text_view)
         tempTextView = view.findViewById<TextView>(R.id.temp_text_view)
         weatherIcon = view.findViewById<ImageView>(R.id.weather_icon)
        temp_minTextView = view.findViewById<TextView>(R.id.temp_min)
        temp_maxTextView = view.findViewById<TextView>(R.id.temp_max)

        pressureTextView = view.findViewById<TextView>(R.id.pressure_text_view)
        humidityTextView = view.findViewById<TextView>(R.id.humidity_text_view)
        speedTextView = view.findViewById<TextView>(R.id.Speed_text_view)


        fetchWeatherData()


        return view
    }


    private fun fetchWeatherData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)

        val call = service.getWeather(cityName, API_KEY, "metric")

        call.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    val weather = response.body()

                    mainTextView.text = weather!!.weather[0].main
                    descriptionTextView.text = weather.weather[0].description
                    tempTextView.text = weather?.main?.temp.toString()
                    temp_minTextView.text = weather?.main?.temp_min.toString()
                    temp_maxTextView.text = weather?.main?.temp_max.toString()


                    pressureTextView.text = weather?.main?.pressure.toString()
                    humidityTextView.text = weather?.main?.humidity.toString()
                    speedTextView.text = weather?.wind?.speed.toString()

                    // Set the weather icon
                    val value = weather.weather[0].icon
                    val resourceId = resources.getIdentifier("_" + value, "drawable", requireContext().packageName)
                    if (resourceId != 0) {
                        // imageView is the imageView where you want to display the icon
                        weatherIcon.setImageResource(resourceId)
                    }

                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
               // Log.e("Retrofit Error", t.toString())

            }
        })
    }



}



