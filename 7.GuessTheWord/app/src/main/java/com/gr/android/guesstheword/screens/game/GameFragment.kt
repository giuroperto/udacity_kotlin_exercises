package com.gr.android.guesstheword.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.gr.android.guesstheword.R
import com.gr.android.guesstheword.databinding.FragmentGameBinding

/**
 * Fragment where the game is played
 */

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        Log.i("GF", "Called ViewModelProviders.of")

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer {
            newEventGameFinish ->
            if (newEventGameFinish) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })

        viewModel.eventBuzz.observe(viewLifecycleOwner, Observer {
            buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })

        return binding.root
    }

//    Called when the game is finished

    private fun gameFinished() {
        val currentScore = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
        action.score = currentScore
        findNavController().navigate(action)
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
//            deprecated in API 26
            buzzer.vibrate(pattern, -1)
            }
        }
    }

}