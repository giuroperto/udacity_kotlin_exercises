package com.gr.android.moodapp.screens.mood

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
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

        setHasOptionsMenu(true)

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

    private fun getShareIntent() : Intent {
        val args = MoodFragmentArgs.fromBundle(requireArguments())
        return ShareCompat.IntentBuilder.from(requireActivity())
                .setText(getString(R.string.share_mood, args.moodOfTheDay))
                .setType("text/plain")
                .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.mood_menu, menu)

//        check if the activity resolves
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
//            hide if it doesnt resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }

        return super.onOptionsItemSelected(item)
    }

}