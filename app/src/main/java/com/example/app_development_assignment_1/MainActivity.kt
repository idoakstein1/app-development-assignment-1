package com.example.app_development_assignment_1

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var board = Array(3) { Array(3) { "" } }
    private var turnCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initButtons()
    }

    private fun initButtons() {
        for (index in 1..9) {
            val cell = findViewById<TextView>(resources.getIdentifier("main_activity_cell_${index}_text_view", "id", packageName))
            cell.setOnClickListener { onCellClick(it as TextView, index) }
        }
    }

    private fun onCellClick(cell: TextView, index: Int) {
        val player = if (turnCounter % 2 == 0) "X" else "O"

        val row = (index - 1) / 3
        val col = (index - 1) % 3

        board[row][col] = player
        cell.text = player

        turnCounter++
    }
}