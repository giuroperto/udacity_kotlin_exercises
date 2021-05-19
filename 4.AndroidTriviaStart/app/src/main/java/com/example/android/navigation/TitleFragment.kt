package com.example.android.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflating and returning the view with DataBindingUtil
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_title,
                container,
                false
        )

//        changing to navigation setonclicklistener
//        binding.playButton.setOnClickListener { view: View ->

//            Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_gameFragment)

//            using extension functions
//            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
//        }

//        navigation clicklistener
        binding.playButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        )

        return binding.root
    }

}