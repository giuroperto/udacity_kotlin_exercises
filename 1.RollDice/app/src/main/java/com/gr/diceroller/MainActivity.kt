package com.gr.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var rollButton: Button
//    private lateinit var resultText: TextView
    private lateinit var diceImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rollButton = findViewById(R.id.roll_button)

        rollButton.text = "Let's Roll"

        rollButton.setOnClickListener {
//            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show()
            rollDice()
        }
    }

    fun rollDice() {
        val randomInt = Random().nextInt(6) + 1

        diceImage = findViewById(R.id.dice_image)

        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        diceImage.setImageResource(drawableResource)

//        resultText = findViewById(R.id.result_text)
//        resultText.text = randomInt.toString()
    }
}