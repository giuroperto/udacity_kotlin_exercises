package com.gr.android.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.gr.android.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.doneBtn.setOnClickListener {
            addNickname()

//            Hide the keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun addNickname() {
        val nickname = binding.nicknameEdit.text
        binding.nicknameText.apply {
            text = nickname
            visibility = View.VISIBLE
        }
        binding.nicknameEdit.visibility = View.GONE
        binding.doneBtn.visibility = View.GONE
    }
}