package com.example.weatherapped.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.weatherapped.MainViewModel
import com.example.weatherapped.R
import com.example.weatherapped.adapters.WeatherAdapter
import com.example.weatherapped.adapters.WeatherModel
import com.example.weatherapped.databinding.FragmentDaysBinding


class DaysFragment : Fragment(), WeatherAdapter.Listener{

    private  lateinit var binding: FragmentDaysBinding
    private  lateinit var adapter: WeatherAdapter
    private  val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.liveDataList.observe(viewLifecycleOwner){
            adapter.submitList(it.subList(1,it.size))
        }
    }
    private fun init() = with(binding){
        adapter = WeatherAdapter(this@DaysFragment)
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            DaysFragment()
    }

    override fun onClick(item: WeatherModel) {
        model.liveDataCurrent.value = item
    }
}