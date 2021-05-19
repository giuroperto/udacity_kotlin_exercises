package com.gr.android.colormyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gr.android.colormyviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setListeners()
    }

    private fun setListeners() {
        val clickableViews: List<View> = listOf(binding.boxOneText, binding.boxTwoText, binding.boxThreeText, binding.boxFourText, binding.boxFiveText,
            binding.constraintLayout, binding.redBtn, binding.greenBtn, binding.yellowBtn)

        for (item in clickableViews) {
            item.setOnClickListener {
                makeColored(it)
            }
        }
    }


    private fun makeColored(view: View) {
        when (view.id) {

//            boxes using Color class colors for background
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)

//            boxes using Android color resources for background
            R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_green_light)
            R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_green_dark)
            R.id.box_five_text -> view.setBackgroundResource(android.R.color.holo_green_light)

//            boxes using custom colors for background
            R.id.red_btn -> binding.boxThreeText.setBackgroundResource(R.color.my_red)
            R.id.green_btn -> binding.boxFourText.setBackgroundResource(R.color.my_green)
            R.id.yellow_btn -> binding.boxFiveText.setBackgroundResource(R.color.my_yellow)

            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }

}