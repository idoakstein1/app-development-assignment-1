package com.example.app_development_assignment_1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var board = Array(3) { Array(3) { "" } }
    private lateinit var currentPlayerTextView: TextView
    private lateinit var restartGameButton: Button
    private var turnCounter = 0
    private var isGameFinished = false

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
        currentPlayerTextView = findViewById(R.id.main_activity_current_player_text_view)
        restartGameButton = findViewById(R.id.main_activity_restart_game_button)
        restartGameButton.setOnClickListener { restartGame() }
    }

    private fun initButtons() {
        for (index in 1..9) {
            val cell = findViewById<TextView>(resources.getIdentifier("main_activity_cell_${index}_text_view", "id", packageName))
            cell.setOnClickListener { onCellClick(it as TextView, index) }
        }
    }

    private fun onCellClick(cell: TextView, index: Int) {
        val row = (index - 1) / 3
        val col = (index - 1) % 3

        if (board[row][col] != "" || isGameFinished) {
            return
        }

        val player = if (turnCounter % 2 == 0) "X" else "O"

        board[row][col] = player
        cell.text = player
        cell.setTextColor(if (turnCounter % 2 == 0) getColor(R.color.red) else getColor(R.color.blue))

        if (isWinner(player, row, col)) {
            currentPlayerTextView.text = "${player} won"
            isGameFinished = true
        }
        if (isDraw()) {
            currentPlayerTextView.text = "Draw"
            isGameFinished = true
        }
        if (isGameFinished) {
            restartGameButton.visibility = Button.VISIBLE
            return
        }

        updateTurn()
    }

    private fun updateTurn() {
        turnCounter++
        val nextPlayer = if (turnCounter % 2 == 0) "X" else "O"
        currentPlayerTextView.text = "Current Player: ${nextPlayer}"
    }

    private fun isDraw(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == "") {
                    return false
                }
            }
        }

        return true
    }

    private fun isWinner(player: String, row: Int, col: Int): Boolean {
        if (board[row].all { it == player }) {
            return true
        }

        if ((0..2).all { board[it][col] == player }) {
            return true
        }

        if (row == col && (0..2).all { board[it][it] == player }) {
            return true
        }

        if (row + col == 2 && (0..2).all { board[it][2 - it] == player }) {
            return true
        }

        return false
    }

    private fun restartGame() {
        board = Array(3) { Array(3) { "" } }
        clearButtons()
        turnCounter = 0
        isGameFinished = false
        currentPlayerTextView.text = "Current Player: X"
        restartGameButton.visibility = Button.INVISIBLE
    }

    private fun clearButtons() {
        for (index in 1..9) {
            val cell = findViewById<TextView>(resources.getIdentifier("main_activity_cell_${index}_text_view", "id", packageName))
            cell.text = ""
        }
    }
}