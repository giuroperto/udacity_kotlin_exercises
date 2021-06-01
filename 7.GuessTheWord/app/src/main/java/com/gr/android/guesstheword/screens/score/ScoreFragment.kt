package com.gr.android.guesstheword.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gr.android.guesstheword.R
import com.gr.android.guesstheword.databinding.FragmentScoreBinding

/**
 * Fragment where the final score is shown, after the game is over
 */

class ScoreFragment : Fragment() {

    private lateinit var binding : FragmentScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)

//        Get args using by navArgs property delegate
//        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
//        binding.scoreText.text = scoreFragmentArgs.score.toString()
        binding.playAgainButton.setOnClickListener { onPlayAgain() }

        return binding.root
    }

    private fun onPlayAgain() {
//        findNavController().navigate(scoreFragment.actionRestart())
    }
}
