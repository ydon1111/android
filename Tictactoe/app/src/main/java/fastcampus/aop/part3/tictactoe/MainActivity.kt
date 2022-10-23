package fastcampus.aop.part3.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import fastcampus.aop.part3.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding

    private val buttons = arrayOfNulls<Button>(9)

    private var activityPlayer: Boolean = true

    private var p1ScoreCount = 0
    private var p2ScoreCount = 0
    private var roundCount = 0

    var gameState: IntArray = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

    var winningPosition: Array<IntArray> =
        arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewMode()

        for ((index, item) in buttons.withIndex()) {

            val buttonID = "btn_${index}"
            val resourceID = resources.getIdentifier(buttonID, "id", packageName)
            buttons[index] = findViewById(resourceID)
            buttons[index]?.setOnClickListener(this)
        }

        binding.resetBtn.setOnClickListener {
            playAgain()

            p1ScoreCount = 0
            p2ScoreCount = 0

            updatePlayerScore()
        }
    }


    override fun onClick(view: View?) {
        if ((view as Button).text.toString() != "") {
            Toast.makeText(applicationContext, "이미 체크됨", Toast.LENGTH_SHORT).show()
            return
        }

        val buttonId = view.resources.getResourceEntryName(view.id)

        val gameStartPointer = buttonId.substring(buttonId.length - 1, buttonId.length).toInt()

        if (activityPlayer) {
            view.text = "X"

            view.setTextColor(Color.parseColor("#FFC34A"))
            gameState[gameStartPointer] = 0
        } else {
            view.text = "O"

            view.setTextColor(Color.parseColor("#70FFEA"))
            gameState[gameStartPointer] = 1
        }

        roundCount++
        if (checkWinner()) {
            if (activityPlayer) {
                p1ScoreCount++
                updatePlayerScore()
                Toast.makeText(this, "플레이어1 승리", Toast.LENGTH_SHORT).show()
                playAgain()
            } else {
                p2ScoreCount++
                updatePlayerScore()
                Toast.makeText(this, "플레이어2 승리", Toast.LENGTH_SHORT).show()
                playAgain()
            }
        } else if (roundCount == 9) {
            playAgain()
            Toast.makeText(this, "무승부", Toast.LENGTH_SHORT).show()
        } else {
            activityPlayer = !activityPlayer
        }
        viewMode()
    }

    private fun viewMode() {
        if (activityPlayer) {
            binding.player1.setTextColor(Color.RED)
            binding.player2.setTextColor(Color.BLACK)
        } else {
            binding.player1.setTextColor(Color.BLACK)
            binding.player2.setTextColor(Color.RED)
        }
    }

    private fun playAgain() {
        roundCount = 0
        activityPlayer = true

        for ((index, item) in buttons.withIndex()) {
            gameState[index] = 2
            buttons[index]?.text = ""

        }
    }

    private fun updatePlayerScore() {
        binding.player1Score.text = p1ScoreCount.toString()
        binding.player2Score.text = p2ScoreCount.toString()
    }

    private fun checkWinner(): Boolean {
        var winnerResult = false
        for (winPosition in winningPosition) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                gameState[winPosition[1]] == gameState[winPosition[2]] &&
                gameState[winPosition[0]] != 2
            ) {
                winnerResult = true
            }
        }
        return winnerResult
    }
}
