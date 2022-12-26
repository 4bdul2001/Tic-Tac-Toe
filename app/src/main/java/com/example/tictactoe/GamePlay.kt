package com.example.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityGamePlayBinding

class GamePlay : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGamePlayBinding

    private val player1 = 1
    private val player1Value = "X"
    private val player2 = 2
    private val player2Value = "0"
    private var activePlayer = player1
    private var gameActive = true
    private lateinit var filledPos: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityGamePlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.filledPos = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)

        this.binding.option0.setOnClickListener(this)
        this.binding.option1.setOnClickListener(this)
        this.binding.option2.setOnClickListener(this)
        this.binding.option3.setOnClickListener(this)
        this.binding.option4.setOnClickListener(this)
        this.binding.option5.setOnClickListener(this)
        this.binding.option6.setOnClickListener(this)
        this.binding.option7.setOnClickListener(this)
        this.binding.option8.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(curView: View?) {
        if (!gameActive) return

        val btnClicked = findViewById<Button>(curView!!.id)
        val btnTag = Integer.parseInt(btnClicked.tag.toString())

        if (this.filledPos[btnTag] != -1) {
            return
        } else {
            this.filledPos[btnTag] = this.activePlayer
        }

        if (this.activePlayer == this.player1){
            btnClicked.text = this.player1Value
            this.activePlayer = this.player2
            this.binding.btnPlayerTurn.text = "Player-2 Turn"
        } else {
            btnClicked.text = this.player2Value
            this.activePlayer = this.player1
            this.binding.btnPlayerTurn.text = "Player-1 Turn"
        }
        this.winChecker()
    }

    /*                      How it all looks for reference
            0   1   2
            3   4   5
            6   7   8
    */
    private fun winChecker() {
        var winnerDecided = false
        for (i in 0..2){
            /*
                    Check Every Row
             */
            val tempR = this.filledPos[i*3]
            if(tempR!=-1 && this.filledPos[(i*3)+1]==tempR && this.filledPos[(i*3)+2]==tempR){
                this.gameOverMessage("Player $tempR Wins!")
                this.gameActive = false
                winnerDecided = true
            }

            /*
                    Check Every Column
             */
            val tempC = this.filledPos[i]
            if(tempC!=-1 && this.filledPos[i+3]==tempC && this.filledPos[i+6]==tempC) {
                this.gameOverMessage("Player $tempC Wins!")
                this.gameActive = false
                winnerDecided = true
            }

            /*
                    Check Left Diagonal
             */
            val tempLeftDiag = this.filledPos[0]
            if (tempLeftDiag!=-1 && this.filledPos[4]==tempLeftDiag && this.filledPos[8]==tempLeftDiag){
                this.gameOverMessage("Player $tempLeftDiag Wins!")
                this.gameActive = false
                winnerDecided = true
            }

            /*
                    Check Right Diagonal
             */
            val tempRightDiag = this.filledPos[2]
            if (tempRightDiag!=-1 && this.filledPos[4]==tempRightDiag && this.filledPos[6]==tempRightDiag){
                this.gameOverMessage("Player $tempRightDiag Wins!")
                this.gameActive = false
                winnerDecided = true
            }
        }

        /*
                Check for DRAW
         */
        if (!winnerDecided){
            var draw = true
            for(i in 0 until this.filledPos.size){
                if(this.filledPos[i]==-1) {
                    draw = false
                    break
                }
            }
            if (draw) this.gameOverMessage("It's a Draw!")
        }
    }

    private fun gameOverMessage(s: String) {
        AlertDialog.Builder(this)
            .setMessage(s)
            .setTitle("Game Over")
            .setPositiveButton("Restart Game") { _, _ ->
                this.restartGame()
            }
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun restartGame() {
        this.filledPos = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
        this.activePlayer = player1
        this.gameActive = true
        this.binding.btnPlayerTurn.text = "Player-1 Turn"
        this.binding.option0.text=""
        this.binding.option1.text=""
        this.binding.option2.text=""
        this.binding.option3.text=""
        this.binding.option4.text=""
        this.binding.option5.text=""
        this.binding.option6.text=""
        this.binding.option7.text=""
        this.binding.option8.text=""
    }


}