package com.example.weatherapped.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.example.weatherapped.DialogManager
import com.example.weatherapped.MainViewModel
import com.example.weatherapped.adapters.VpAdapter
import com.example.weatherapped.adapters.WeatherModel
import com.example.weatherapped.databinding.FragmentMainBinding
import com.example.weatherapped.retrofit.WeatherApi
import com.example.weatherapped.retrofit.WeatherDataModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val API_KEY = "5bcb8f0ada1b48b5b76132313241301"
class MainFragment : Fragment() {
    private  lateinit var fLocationClient : FusedLocationProviderClient
    private val fList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tList = listOf(
        "Hours",
        "Days"
    )
    private lateinit var binding: FragmentMainBinding
    private  lateinit var pLauncher: ActivityResultLauncher<String>
    private  val model: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        updateCurrentCard()
        getLocation()
    }

    private fun updateCurrentCard() = with(binding){
        model.liveDataCurrent.observe(viewLifecycleOwner){
            val maxMinTemp = "${it.minTemp}°C/${it.maxTemp}°C"
            val curTemp = "${it.currentTemp}°C"
            tvData.text = "Updated in: ${it.time}"
            tvCity.text = it.city
            tvCurrentTemp.text = if(it.currentTemp.isEmpty()) maxMinTemp else curTemp
            tvCondition.text = it.condition
            tvMaxMin.text = if(it.currentTemp.isEmpty()) "" else maxMinTemp
            Picasso.get().load("https:" + it.imageUrl).into(imWeather)
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }

    private fun init() = with(binding){
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
            tab, pos -> tab.text = tList[pos]
        }.attach()
        ibSync.setOnClickListener{
            tabLayout.selectTab(tabLayout.getTabAt(0))
            checkLocation()
        }
        ibSearch.setOnClickListener{
            DialogManager.searchByNameDialog(requireContext(), object : DialogManager.Listener{
                override fun onClick(name: String?) {
                    name?.let { it1 -> requestWeatherData(it1)}
                }
            })
        }
    }

    private fun checkLocation(){
        if(isLocationEnabled()) {
            getLocation()
        } else{
            DialogManager.locationSettingsDialog(requireContext(), object: DialogManager.Listener{
                override fun onClick(name: String?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
        }
    }

    private fun isLocationEnabled(): Boolean{
        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    private fun getLocation(){
        if(!isLocationEnabled()){
            Toast.makeText(requireContext(), "Location Disabled", Toast.LENGTH_SHORT).show()
            return
        }
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
            fLocationClient
                .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token).addOnCompleteListener{
                    requestWeatherData("${it.result.latitude},${it.result.longitude},")
                }
    }

    private fun permissionListener(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(){
        if(!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION))
        {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    private fun requestWeatherData(city: String) {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val gson: Gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        val weatherApi = retrofit.create<WeatherApi.WeatherApi>()

        CoroutineScope(Dispatchers.IO).launch {
            val call = weatherApi.getWeatherData(
                apiKey = API_KEY,
                city = city,
                days = "3",
                aqi = "no",
                alerts = "no"
            )
            convertedCall(call)
        }
    }

    private  fun convertedCall(call: WeatherDataModel){
        val list = convertedCallDay(call)
        parseCurrentData(call, list[0])
    }

    private fun convertedCallDay(call: WeatherDataModel) : List<WeatherModel>{
        val list = ArrayList<WeatherModel>()

        for(i in 0 until call.forecast.forecastday.size){
            val logged = call.forecast.forecastday[i].hour
            val item = WeatherModel(
                call.location.name,
                call.forecast.forecastday[i].date,
                call.forecast.forecastday[i].day.condition.text,
                "",
                call.forecast.forecastday[i].day.maxtemp_c,
                call.forecast.forecastday[i].day.mintemp_c,
                call.forecast.forecastday[i].day.condition.icon,
                logged
            )
            Log.d("MyLog", "Error: $logged")
            list.add(item)
        }
        model.liveDataList.postValue(list)
        return list
    }
    private  fun parseCurrentData(call: WeatherDataModel, weatherItem: WeatherModel){
        val item = WeatherModel(
            call.location.name,
            call.current.last_updated,
            call.current.condition.text,
            call.current.temp_c.toString(),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            call.current.condition.icon,
            weatherItem.hours
        )
        model.liveDataCurrent.postValue(item)
    }
    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}