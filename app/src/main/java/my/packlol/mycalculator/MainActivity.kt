package my.packlol.mycalculator

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import my.packlol.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainActivityViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.text.collect { equation ->
                binding.tvInput.text = equation
            }
        }

        setDigitBtnOnClickListeners(OnDigitListener())

        binding.btnClear.setOnClickListener {
            viewModel.onClear()
        }
        binding.btnDecimal.setOnClickListener {
            viewModel.onDecimal()
        }

        binding.btnMultiply.setOnClickListener {
            viewModel.onOpertor("*")
        }
        binding.btnDivide.setOnClickListener {
            viewModel.onOpertor("/")
        }
        binding.btnSubtract.setOnClickListener {
            viewModel.onOpertor("-")
        }
        binding.btnAdd.setOnClickListener {
            viewModel.onOpertor("+")
        }

        binding.btnEqual.setOnClickListener {
            viewModel.onEqual()
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
    }

    inner class OnDigitListener : OnClickListener {

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