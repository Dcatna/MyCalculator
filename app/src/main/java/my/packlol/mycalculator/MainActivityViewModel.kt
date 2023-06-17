package my.packlol.mycalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    private var lastNumeric = false
    private var lastDot = false


    private val mutableText = MutableStateFlow("")
    val text = mutableText.asStateFlow()

    fun onDigit(value: String) {
        viewModelScope.launch {
            mutableText.emit(mutableText.value + value)
            lastNumeric = true
            lastDot = false
        }
    }
    fun onClear() {
        viewModelScope.launch {
            mutableText.emit("")
        }
    }
    fun onDecimal() {
        viewModelScope.launch {
            if(lastNumeric && !lastDot && mutableText.value.count { it == '.' } == 0) {
                mutableText.emit(mutableText.value + ".")
                lastNumeric = false
                lastDot = true
            }
        }
    }
    fun onOperator(operator: String) {
        viewModelScope.launch {
            if(lastNumeric && !isOperatorAdded(mutableText.value)){
                mutableText.emit(mutableText.value + operator)
                lastNumeric = false
                lastDot = false
            }
        }
    }


    private fun isOperatorAdded(equation : String) : Boolean{
        if(equation.startsWith("-")) {
            return false
        }
        return listOf("/", "*", "+", "-").any { operator ->
            equation.contains(operator)
        }
    }

    fun onEqual() {
        viewModelScope.launch {
            if (lastNumeric) {

                var tvValue = mutableText.value
                var prefix = ""

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                val result: Double? = tvValue
                    .find { it in listOf('/', '*', '+', '-') }
                    ?.let { operator ->

                    val (one, two) = tvValue.split(operator)

                    val left = (prefix + one).toDoubleOrNull() ?: return@let null
                    val right = two.toDoubleOrNull() ?: return@let null

                    when (operator) {
                        '+' -> left + right
                        '-' -> left - right
                        '*' -> left * right
                        '/' -> left / right
                        else -> null
                    }
                }
                result?.let { mutableText.emit(removeDotZero(it.toString())) }
            }
        }
    }

    private fun removeDotZero(result : String) : String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length-2)
        }
        return value
    }


}