package my.packlol.mycalculator

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.bindingProperty
import kotlinx.coroutines.launch

class MainActivityViewModel: BindingViewModel() {

    private var lastNumeric = false
    private var lastDot = false

    @get:Bindable
    var editText: String by bindingProperty("")
        private set

    fun onDigit(value: String) {
        viewModelScope.launch {
            editText += value
            lastNumeric = true
            lastDot = false
        }
    }
    fun onClear() {
        viewModelScope.launch {
            editText = ""
        }
    }

    private fun String.decimalsAfterOperator(operatorIdx: Int) = this
        .substring(operatorIdx, this.lastIndex).count { it == '.' }

    fun onDecimal() {
        viewModelScope.launch {
            val equation = editText
            if(lastNumeric && !lastDot) {
                val operatorIdx = equation.indexOfFirst { it in listOf('/', '*', '+', '-') }

                if (operatorIdx == -1 && equation.count { it == '.' } > 0) {
                    return@launch
                } else if(operatorIdx != -1 && equation.decimalsAfterOperator(operatorIdx) > 0) {
                    return@launch
                }

                editText += "."
                lastNumeric = false
                lastDot = true
            }
        }
    }
    fun onOperator(operator: String) {
        viewModelScope.launch {
            if(lastNumeric && !isOperatorAdded(editText)){
                editText += operator
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

                var tvValue = editText
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
                result?.let { editText = removeDotZero(it.toString()) }
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