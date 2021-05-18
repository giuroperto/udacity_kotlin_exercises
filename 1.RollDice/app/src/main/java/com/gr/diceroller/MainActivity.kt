package com.gr.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gr.diceroller.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

//    private lateinit var rollButton: Button
//    private lateinit var resultText: TextView
//    private lateinit var diceImage: ImageView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        rollButton = findViewById(R.id.roll_button)

//        using view binding instead
        binding.rollButton.text = "Let's Roll"

        binding.rollButton.setOnClickListener {
//            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show()
            rollDice()
        }
    }

    fun rollDice() {
        val randomInt = Random().nextInt(6) + 1

//        diceImage = findViewById(R.id.dice_image)

        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

//        using view binding instead
        binding.diceImage.setImageResource(drawableResource)

//        resultText = findViewById(R.id.result_text)
//        resultText.text = randomInt.toString()
    }
}