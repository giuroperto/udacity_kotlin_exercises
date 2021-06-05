package com.gr.android.moodapp.screens.moodPicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gr.android.moodapp.MoodPickerFragmentDirections
import com.gr.android.moodapp.R
import com.gr.android.moodapp.databinding.FragmentMoodPickerBinding

const val KEY_MOOD = "key_mood"

class MoodPickerFragment : Fragment() {

    private lateinit var binding: FragmentMoodPickerBinding
    private var moodCount: IntArray = intArrayOf(0, 0, 0, 0)
    private var selectedMood: View? = null
    private lateinit var passMood: String
    private lateinit var viewModel : MoodPickerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mood_picker, container, false)

        viewModel = ViewModelProvider(this).get(MoodPickerViewModel::class.java)

        moodListener()

        binding.saveBtn.setOnClickListener {
            selectedMood?.let {
                viewModel.countClick(whichMood(it))
//                Toast.makeText(context, "Count ${moodCount}", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.navigate(MoodPickerFragmentDirections.actionMoodPickerFragmentToMoodFragment(passMood, moodCount))
            }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(KEY_MOOD, passMood)
    }

     private fun moodListener() {
         val clickableMoods: List<View> = listOf(binding.angryImg, binding.smugImg, binding.uwuImg, binding.yummyImg)

         for (item in clickableMoods) {
             item.setOnClickListener {
                 changeBackground(it)
                 greyAllOut(it, clickableMoods)
                 selectMood(it)
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


    // TODO: 22/05/2021 refactor this part
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

    private fun selectMood(view: View) {
        selectedMood = view
    }

    private fun whichMood(view : View) : String {
        return when (view.id) {
            R.id.angry_img -> "Angry"
            R.id.yummy_img -> "Hungry"
            R.id.uwu_img -> "Relaxed"
            R.id.smug_img -> "Smug"
            else -> "Other"
        }
    }

}