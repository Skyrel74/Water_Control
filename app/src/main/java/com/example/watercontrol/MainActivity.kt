package com.example.watercontrol

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.math.RoundingMode


class MainActivity : AppCompatActivity() {

    private var currentDrunkWater: Double = .0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drunkEt.doOnTextChanged { text, _, _, _ ->
            text.toString().removeAfter2Decimal(drunkEt)
        }

        addBtn.setOnClickListener {
            val drunkWater = drunkEt.text
            if (drunkWater.isNotEmpty() && drunkWater != null) {
                currentDrunkWater += drunkWater.toString().toDouble()
                drunkEt.setText("")
                resultTv.text = removeTrailingZeroes(BigDecimal(currentDrunkWater).setScale(2, RoundingMode.HALF_EVEN))
            }
        }

        delBtn.setOnClickListener {
            val drunkWater = drunkEt.text
            if (drunkWater.isNotEmpty() && drunkWater != null) {
                if (currentDrunkWater > drunkWater.toString().toDouble()) {
                    currentDrunkWater -= drunkWater.toString().toDouble()
                    drunkEt.setText("")
                    resultTv.text = removeTrailingZeroes(BigDecimal(currentDrunkWater).setScale(2, RoundingMode.HALF_EVEN))
                } else
                    Toast.makeText(this, "Число воды в организме слишком мало", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun String.removeAfter2Decimal(et: EditText) {
        if (this.isNullOrEmpty() || this.isNullOrBlank() || this.toLowerCase() == "null") {
            return
        } else {
            if(this.contains(".")) {
                var lastPartOfText = this.split(".")[this.split(".").size - 1]

                if (lastPartOfText.count() > 2) {
                    try {
                        lastPartOfText = this.substring(0, this.indexOf(".") + 3)
                        et.setText(lastPartOfText)
                        et.setSelection(lastPartOfText.length)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else return
            } else return
        }
    }

    private fun removeTrailingZeroes(d: BigDecimal): String? {
        val s = d.toString()
        return "${s.replace("0+$".toRegex(), "")} л"
    }
}