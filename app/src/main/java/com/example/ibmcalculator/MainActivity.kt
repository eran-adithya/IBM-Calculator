import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ibmcalculator.R
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var operand1: Double? = null
    private var operand2: Double? = null
    private var operator: Char? = null
    private var isResultDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf<Int>(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
            R.id.button_add, R.id.button_subtract, R.id.button_multiply, R.id.button_divide,
            R.id.button_equals, R.id.button_clear
        )

        buttons.forEach { buttonId ->
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { view -> onButtonClick(view) }
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        val text = button.text.toString()

        when {
            text.matches("[0-9]".toRegex()) -> onDigitClick(text.toInt())
            text == "C" -> onClearClick()
            text == "+" || text == "-" || text == "*" || text == "/" -> onOperatorClick(text[0])
            text == "=" -> onEqualsClick()
        }
    }

    private fun onDigitClick(digit: Int) {
        if (isResultDisplayed) {
            display.text = ""
            isResultDisplayed = false
        }
        display.append(digit.toString())
    }

    private fun onClearClick() {
        display.text = ""
        operand1 = null
        operand2 = null
        operator = null
        isResultDisplayed = false
    }

    private fun onOperatorClick(operator: Char) {
        if (isResultDisplayed) {
            onClearClick()
        }

        try {
            operand1 = display.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            // Ignore
        }

        this.operator = operator
        display.text = ""
    }

    private fun onEqualsClick() {
        try {
            operand2 = display.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            // Ignore
        }

        if (operand1 != null && operand2 != null && operator != null) {
            val result = when (operator) {
                '+' -> operand1!! + operand2!!
                '-' -> operand1!! - operand2!!
                '*' -> operand1!! * operand2!!
                '/' -> operand1!! / operand2!!
                else -> throw IllegalStateException("Invalid operator: $operator")
            }

            display.text = result.toString()
            operand1 = null
            operand2 = null
            operator = null
            isResultDisplayed = true
        }
    }
}