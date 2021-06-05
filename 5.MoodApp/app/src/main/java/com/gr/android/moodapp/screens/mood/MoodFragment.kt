package com.gr.android.moodapp.screens.mood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gr.android.moodapp.MoodFragmentArgs
import com.gr.android.moodapp.R
import com.gr.android.moodapp.databinding.FragmentMoodBinding

class MoodFragment : Fragment() {

    private lateinit var binding: FragmentMoodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mood, container, false)

        useArgs()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun useArgs() {
        val args = MoodFragmentArgs.fromBundle(requireArguments())

        binding.apply {
            angryTxt.text = args.moodCounts[0].toString()
            smugTxt.text = args.moodCounts[1].toString()
            uwuTxt.text = args.moodCounts[2].toString()
            yummyTxt.text = args.moodCounts[3].toString()
        }

        Toast.makeText(context, "Today you are feeling ${args.moodOfTheDay}.", Toast.LENGTH_SHORT).show()

    }

}