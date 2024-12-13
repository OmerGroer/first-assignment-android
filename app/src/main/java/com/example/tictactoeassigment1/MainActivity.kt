package com.example.tictactoeassigment1

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainLayout: ConstraintLayout = findViewById(R.id.main)
        val title: TextView = findViewById(R.id.title)
        val textViews: Array<Array<TextView>> = Array(3) { i -> Array(3) { j ->
            mainLayout.findViewWithTag((i * 3 + j + 1).toString())
        } }

        for (i in 0..2) {
            for (j in 0..2) {
                textViews[i][j].setOnClickListener { view -> clicked(view as TextView, textViews, title) }
            }
        }
    }

    private fun clicked(tile: TextView, textViews: Array<Array<TextView>>, title: TextView) {
        if (counter % 2 == 0) {
            tile.text = "X"
            tile.setTextColor(Color.parseColor("#872929"))
            title.text = "O Play"
            counter++
            if (check("X", textViews)) winAlert("The X Win!", textViews, title)
            else if (counter == 9) winAlert("It is a Tie!", textViews, title)
        } else {
            tile.text = "O"
            tile.setTextColor(Color.parseColor("#314089"))
            title.text = "X Play"
            counter++
            if (check("O", textViews)) {
                winAlert("The O Win!", textViews, title)
            } else if (counter == 9) {
                winAlert("It is a Tie!", textViews, title)
            }
        }
        tile.isClickable = false
    }

    private fun check(player: String, textViews: Array<Array<TextView>>): Boolean {
        var checkCounter = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (textViews[i][j].text === player) checkCounter++
            }
            if (checkCounter == 3) return true
            checkCounter = 0
        }
        checkCounter = 0
        for (j in 0..2) {
            for (i in 0..2) {
                if (textViews[i][j].text === player) checkCounter++
            }
            if (checkCounter == 3) return true
            checkCounter = 0
        }
        checkCounter = 0
        var j = 0
        for (i in 0..2) {
            if (textViews[i][j].text === player) checkCounter++
            j++
        }
        if (checkCounter == 3) return true
        j = 2
        checkCounter = 0
        for (i in 0..2) {
            if (textViews[i][j].text === player) checkCounter++
            j--
        }
        return checkCounter == 3
    }

    private fun winAlert(text: String, textViews: Array<Array<TextView>>, title: TextView) {
        for (i in 0..2) for (j in 0..2) textViews[i][j].isClickable = false
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setTitle(text)
        dialog.setPositiveButton("Play Again"
        ) { _, _ ->
            for (z in 0..2) for (j in 0..2) {
                textViews[z][j].isClickable = true
                textViews[z][j].text = ""
            }
            counter = 0
            title.text = "X Play"
        }
        dialog.show()
    }
}