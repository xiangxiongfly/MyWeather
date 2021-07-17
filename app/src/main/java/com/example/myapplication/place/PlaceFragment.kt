package com.example.myapplication.place

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.DataCache
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentPlaceBinding
import com.example.myapplication.entity.Place
import com.example.myapplication.exceptions.ServerException
import com.example.myapplication.network.DataResult
import com.example.myapplication.utils.toast
import com.example.myapplication.weather.WeatherActivity

class PlaceFragment : Fragment() {
    private lateinit var binding: FragmentPlaceBinding

    private lateinit var mContext: Context

    private val placeViewModel: PlaceViewModel by viewModels()

    private val placeList = ArrayList<Place>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlaceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is MainActivity && DataCache.isSaved()) {
            val place = DataCache.getPlace()
            WeatherActivity.actionStart(mContext,
                place.name,
                place.location.lng,
                place.location.lat)
            activity?.finish()
            return
        }

        val adapter = PlaceAdapter(mContext, placeList)
        binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : PlaceAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val place = placeList[position]
                DataCache.savePlace(place)
                when (activity) {
                    is MainActivity -> {
                        WeatherActivity.actionStart(mContext,
                            place.name,
                            place.location.lng,
                            place.location.lat)
                        activity?.finish()
                    }
                    is WeatherActivity -> {
                        val weatherActivity = activity as WeatherActivity
                        weatherActivity.closeDrawers()
                        weatherActivity.weatherModel.lng = place.location.lng
                        weatherActivity.weatherModel.lat = place.location.lat
                        weatherActivity.weatherModel.placeName = place.name
                        weatherActivity.refreshWeather()
                    }
                }
            }
        })

        binding.search.addTextChangedListener { it ->
            val content = it.toString()
            if (content.isNotEmpty()) {
                placeViewModel.searchPlaces(content).observe(this) { it ->
                    when (it) {
                        is DataResult.Success -> {
                            adapter.setData(it.response.places)
                        }
                        is DataResult.Error -> {
                            if (it.exception.throwable is ServerException) {
                                toast(mContext, "为查询到相关地址")
                            } else {
                                toast(mContext, it.exception.msg)
                            }
                        }
                    }
                }
            } else {
                adapter.setData(ArrayList<Place>())
            }
        }
    }
}