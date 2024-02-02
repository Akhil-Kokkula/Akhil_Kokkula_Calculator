package com.example.akhil_kokkula_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun numberAndOpsAction(view: View) {
        val display : TextView = findViewById(R.id.displayScreen)
        val button = view as Button
        display.append(button.text.toString())
    }

    fun calculateOp(view: View) {
        val display : TextView = findViewById(R.id.displayScreen)
        var calcStr = display.text.toString()
        //var numsArr = emptyArray<Float>()
        var numsStack = ArrayDeque<Float>()
        var opsStack = ArrayDeque<Char>()
        //var ops = emptyArray<Char>()
        var endNumIndex = calcStr.length
        for (i in calcStr.length - 1 downTo 0) {
            if (calcStr[i] == '+' || calcStr[i] == '-' || calcStr[i] == '*' || calcStr[i] == '/') {
                println(calcStr[i])
                println(calcStr.substring(i + 1, endNumIndex))
                if (calcStr[i + 1] == '√') {
                    numsStack.addLast(sqrt(calcStr.substring(i + 2, endNumIndex).toFloat()))
                } else {
                    numsStack.addLast(calcStr.substring(i + 1, endNumIndex).toFloat())
                }
                opsStack.addLast(calcStr[i])
                endNumIndex = i
            } else if (i == 0) {
                if (calcStr[i] == '√') {
                    numsStack.addLast(sqrt(calcStr.substring(i + 1, endNumIndex).toFloat()))
                } else {
                    numsStack.addLast(calcStr.substring(i, endNumIndex).toFloat())
                }
            }
        }

        println(numsStack.toString())
        println(opsStack.toString())

        while(numsStack.size != 1) {
            println(numsStack.toString())
            println(opsStack.toString())
            val num1 = numsStack.removeLast()
            val op = opsStack.removeLast()

            if (op == '√') {
                numsStack.addLast(sqrt(num1))
                continue
            }

            val num2 = numsStack.removeLast()
            if (op == '+') {
                if (opsStack.size != 0 && opsStack[opsStack.size - 1] == '√') {
                    opsStack.removeLast()
                    numsStack.addLast(num1 + sqrt(num2))
                } else {
                    numsStack.addLast(num1 + num2)
                }
            }
            else if (op == '-') {
                if (opsStack.size != 0 && opsStack[opsStack.size - 1] == '√') {
                    opsStack.removeLast()
                    numsStack.addLast(num1 - sqrt(num2))
                } else {
                    numsStack.addLast(num1 - num2)
                }
            }
            else if (op == '*') {
                if (opsStack.size != 0 && opsStack[opsStack.size - 1] == '√') {
                    opsStack.removeLast()
                    numsStack.addLast(num1 * sqrt(num2))
                } else {
                    numsStack.addLast(num1 * num2)
                }
            }
            else if (op == '/') {
                if (num2 == 0f) {
                    Toast.makeText(this@MainActivity,
                        "Division by 0 error",
                        Toast.LENGTH_LONG).show()
                    return
                }
                if (opsStack.size != 0 && opsStack[opsStack.size - 1] == '√') {
                    opsStack.removeLast()
                    numsStack.addLast(num1 / sqrt(num2))
                } else {
                    numsStack.addLast(num1 / num2)
                }
            }

        }

        display.setText(numsStack[0].toString())
    }

    fun allClearAction(view: View) {
        val display : TextView = findViewById(R.id.displayScreen)
        display.setText("")
    }
}