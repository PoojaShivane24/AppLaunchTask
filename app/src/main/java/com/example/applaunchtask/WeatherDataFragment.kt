package com.example.applaunchtask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.applaunchtask.databinding.FragmentWeatherDataBinding
import com.example.applaunchtask.viewmodel.UserViewModel
import java.util.*

class WeatherDataFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    lateinit var fragmentWeatherDataBinding: FragmentWeatherDataBinding
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentWeatherDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_weather_data, container, false)

        return fragmentWeatherDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences = requireContext().getSharedPreferences("UserSharedPreference", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        var navController : NavController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.getWeatherData()

        viewModel.weather.observe(viewLifecycleOwner) {
            val dataToday = it[0]
            val dataTomorrow = it[1]

            fragmentWeatherDataBinding.firstData.tvDate.text = getDate(dataToday.dt)
            fragmentWeatherDataBinding.firstData.tvDay.text = dataToday.temp.day.toString()
            fragmentWeatherDataBinding.firstData.tvMin.text = dataToday.temp.min.toString()
            fragmentWeatherDataBinding.firstData.tvMax.text = dataToday.temp.max.toString()
            fragmentWeatherDataBinding.firstData.tvNight.text = dataToday.temp.night.toString()
            fragmentWeatherDataBinding.firstData.tvEve.text = dataToday.temp.eve.toString()
            fragmentWeatherDataBinding.firstData.tvMorn.text = dataToday.temp.morn.toString()
            fragmentWeatherDataBinding.firstData.tvHumidity.text = dataToday.humidity.toString()
            fragmentWeatherDataBinding.firstData.tvWeatherType.text = dataToday.weather[0].description
            fragmentWeatherDataBinding.firstData.tvWindSpeed.text = dataToday.wind_speed.toString()

            fragmentWeatherDataBinding.secondData.tvDate.text = getDate(dataTomorrow.dt)
            fragmentWeatherDataBinding.secondData.tvDay.text = dataTomorrow.temp.day.toString()
            fragmentWeatherDataBinding.secondData.tvMin.text = dataTomorrow.temp.min.toString()
            fragmentWeatherDataBinding.secondData.tvMax.text = dataTomorrow.temp.max.toString()
            fragmentWeatherDataBinding.secondData.tvNight.text = dataTomorrow.temp.night.toString()
            fragmentWeatherDataBinding.secondData.tvEve.text = dataTomorrow.temp.eve.toString()
            fragmentWeatherDataBinding.secondData.tvMorn.text = dataTomorrow.temp.morn.toString()
            fragmentWeatherDataBinding.secondData.tvHumidity.text = dataTomorrow.humidity.toString()
            fragmentWeatherDataBinding.secondData.tvWeatherType.text = dataTomorrow.weather[0].description
            fragmentWeatherDataBinding.secondData.tvWindSpeed.text = dataTomorrow.wind_speed.toString()

        }


    }




    private fun getDate(timestamp: Long) :String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format("dd-MM-yyyy",calendar).toString()
        return date
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                editor.putBoolean("isLoggedIn", false)
                editor.apply()
                findNavController().navigate(R.id.action_weatherDataFragment_to_loginFragment)
            }
        }
        return true
    }



}