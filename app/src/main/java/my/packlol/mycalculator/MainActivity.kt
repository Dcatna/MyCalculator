package my.packlol.mycalculator

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.skydoves.bindables.BindingActivity
import my.packlol.mycalculator.databinding.ActivityMainBinding


class MainActivity:  BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        setOnOperatorClickListeners()
        setDigitBtnOnClickListeners(OnDigitListener())
        setActionButtonListeners()

        binding {
            lifecycleOwner = this@MainActivity
            viewModel = viewModel
        }
    }

    private fun setActionButtonListeners() {
        binding.btnClear.setOnClickListener {
            viewModel.onClear()
        }
        binding.btnDecimal.setOnClickListener {
            viewModel.onDecimal()
        }
        binding.btnEqual.setOnClickListener {
            viewModel.onEqual()
        }
    }

    private fun setOnOperatorClickListeners() {
        binding.btnMultiply.setOnClickListener {
            viewModel.onOperator("*")
        }
        binding.btnDivide.setOnClickListener {
            viewModel.onOperator("/")
        }
        binding.btnSubtract.setOnClickListener {
            viewModel.onOperator("-")
        }
        binding.btnAdd.setOnClickListener {
            viewModel.onOperator("+")
        }

    }

    private fun setDigitBtnOnClickListeners(
        onDigitListener: OnDigitListener
    ) {
        binding.btnOne.setOnClickListener(onDigitListener)
        binding.btnTwo.setOnClickListener(onDigitListener)
        binding.btnThree.setOnClickListener(onDigitListener)
        binding.btnFour.setOnClickListener(onDigitListener)
        binding.btnFive.setOnClickListener(onDigitListener)
        binding.btnSix.setOnClickListener(onDigitListener)
        binding.btnSeven.setOnClickListener(onDigitListener)
        binding.btnEight.setOnClickListener(onDigitListener)
        binding.btnNine.setOnClickListener(onDigitListener)
        binding.btnZero.setOnClickListener(onDigitListener)
    }

    inner class OnDigitListener : View.OnClickListener {

        override fun onClick(v: View?) {
            viewModel.onDigit(
                when (v) {
                    binding.btnOne -> "1"
                    binding.btnTwo -> "2"
                    binding.btnThree -> "3"
                    binding.btnFour -> "4"
                    binding.btnFive -> "5"
                    binding.btnSix -> "6"
                    binding.btnSeven -> "7"
                    binding.btnEight -> "8"
                    binding.btnNine -> "9"
                    binding.btnZero -> "0"
                    else -> return
                }
            )
        }
    }
}