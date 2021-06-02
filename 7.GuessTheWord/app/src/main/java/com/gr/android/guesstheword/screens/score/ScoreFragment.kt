package com.gr.android.guesstheword.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gr.android.guesstheword.R
import com.gr.android.guesstheword.databinding.FragmentScoreBinding
import com.gr.android.guesstheword.screens.score.ScoreFragmentDirections.actionRestart

/**
 * Fragment where the final score is shown, after the game is over
 */

class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    private lateinit var binding : FragmentScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)

        viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ScoreViewModel::class.java)

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer {
            isPlayAgain ->
            if (isPlayAgain) {
                onPlayAgain()
                viewModel.eventFinishedComplete()
            }
        })

        viewModel.finalScoreLiveData.observe(viewLifecycleOwner, Observer {
            finalScore -> binding.scoreText.text = finalScore.toString()
        })

        binding.playAgainButton.setOnClickListener {
            viewModel.eventIsFinished()
        }

        return binding.root
    }

    private fun onPlayAgain() {
        findNavController().navigate(ScoreFragmentDirections.actionRestart())
    }
}
