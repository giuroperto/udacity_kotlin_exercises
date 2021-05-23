package com.gr.android.moodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.gr.android.moodapp.databinding.FragmentMoodPickerBinding

class MoodPickerFragment : Fragment() {

    private lateinit var binding: FragmentMoodPickerBinding
    private var moodCount: IntArray = intArrayOf(0, 0, 0, 0)
    private var selectedMood: View? = null
    private lateinit var passMood: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mood_picker, container, false)

        moodListener()

        binding.saveBtn.setOnClickListener {
            selectedMood?.let {
                countClick(it)
//                Toast.makeText(context, "Count ${moodCount}", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.navigate(MoodPickerFragmentDirections.actionMoodPickerFragmentToMoodFragment(passMood, moodCount))
            }
        }

        return binding.root
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

    private fun countClick(view: View) {
        when (view.id) {
            R.id.angry_img -> {
                moodCount[0] += 1
                passMood = "Angry"
            }
            R.id.yummy_img -> {
                moodCount[3] += 1
                passMood = "Hungry"
            }
            R.id.uwu_img -> {
                moodCount[2] += 1
                passMood = "Relaxed"
            }
            R.id.smug_img -> {
                moodCount[1] += 1
                passMood = "Smug"
            }
        }
    }

    private fun selectMood(view: View) {
        selectedMood = view
    }

}