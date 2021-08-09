/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */

//    we need a reference to the app that this fragment is attached to, to pass in to the vm factory
//    provider

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

//        requireNotNull is a kotlin function that throws and illegal argument exception if the
//        value is null
        val application = requireNotNull(this.activity).application

//        we need a reference to a data source via a reference to the DAO
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

//        create an instance of the viewmodelfactory
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

//        having a factory, we can ask the vmprovider for a vm, passing in the factory and requesting
//        an instance of the viewmodel
//        specify to use the factory to build it, and get an instance of the vm class java
        val sleepTrackerViewModel = ViewModelProvider(this, viewModelFactory)
                .get(SleepTrackerViewModel::class.java)

//        specify the current activity as the lifecycle owner of the binding
//        it is necessary so that the binding can observe livedata updates
        binding.lifecycleOwner = this

//        our layout needs to know about the vm -> then we can reference functions and data in the
//        vm from the layout to display livedata

//        set the variable in the view which we access through the binding object to the viewmodel
        binding.sleepTrackerViewModel = sleepTrackerViewModel

//        we now need to observe navigate to sleep quality, so we know when to navigate
//        we add an observer on our navigateToSleepQuality state variable
        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer {
//            when we observe a change, inside here we navigate and call doneNavigating
            night ->
            night?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                sleepTrackerViewModel.doneNavigating()
            }
        })

//        adding the observer to the snackbar
        sleepTrackerViewModel.showSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true
                Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT
                ).show()
                sleepTrackerViewModel.doneShowingSnackbar()
            }
        })

        val adapter = SleepNightAdapter()
        binding.rvSleepList.adapter = adapter

        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        val manager = GridLayoutManager(activity, 3)
        binding.rvSleepList.layoutManager = manager

        return binding.root
    }
}
