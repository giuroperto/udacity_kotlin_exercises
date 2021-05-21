package com.gr.android.moodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gr.android.moodapp.databinding.FragmentMoodPickerBinding

class moodPickerFragment : Fragment() {

    private lateinit var binding: FragmentMoodPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mood_picker, container, false)

        moodListener()

        return binding.root
    }

    private fun moodListener() {
        val clickableMoods: List<View> = listOf(binding.angryImg, binding.smugImg, binding.uwuImg, binding.yummyImg)

        for (item in clickableMoods) {
            item.setOnClickListener {
                changeBackground(it)
                greyAllOut(it, clickableMoods)
            }
        }
    }

    private fun changeBackground(view: View) {
        when (view.id) {
            R.id.angry_img -> binding.moodPickerConstraintLayout.setBackgroundResource(R.color.bg_red)
            R.id.yummy_img -> binding.moodPickerConstraintLayout.setBackgroundResource(R.color.bg_orange)
            R.id.uwu_img -> binding.moodPickerConstraintLayout.setBackgroundResource(R.color.bg_yellow)
            R.id.smug_img -> binding.moodPickerConstraintLayout.setBackgroundResource(R.color.bg_green)

            else -> binding.moodPickerConstraintLayout.setBackgroundResource(R.color.white)
        }
    }

    private fun greyAllOut(view: View, list: List<View>) {
        for (item in list) {
            if (item.id != view.id) {
                item.rotation = 45.0F

                binding.apply {
                    grayFilterAngry.visibility = View.VISIBLE
                    grayFilterUwu.visibility = View.VISIBLE
                    grayFilterSmug.visibility = View.VISIBLE
                    grayFilterYummy.visibility = View.VISIBLE
                }

                when (view.id) {
                    R.id.angry_img -> binding.grayFilterAngry.visibility = View.GONE
                    R.id.yummy_img -> binding.grayFilterYummy.visibility = View.GONE
                    R.id.uwu_img -> binding.grayFilterUwu.visibility = View.GONE
                    R.id.smug_img -> binding.grayFilterSmug.visibility = View.GONE
                }
            } else {
                item.rotation = 0.0F

                binding.apply {
                    grayFilterAngry.visibility = View.VISIBLE
                    grayFilterUwu.visibility = View.VISIBLE
                    grayFilterSmug.visibility = View.VISIBLE
                    grayFilterYummy.visibility = View.VISIBLE
                }

                when (view.id) {
                    R.id.angry_img -> binding.grayFilterAngry.visibility = View.GONE
                    R.id.yummy_img -> binding.grayFilterYummy.visibility = View.GONE
                    R.id.uwu_img -> binding.grayFilterUwu.visibility = View.GONE
                    R.id.smug_img -> binding.grayFilterSmug.visibility = View.GONE
                }
            }
        }
    }

}