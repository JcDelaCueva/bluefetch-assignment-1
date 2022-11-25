package com.jcdelacueva.bluefetchassignment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.GridLayoutManager
import com.jcdelacueva.bluefetchassignment1.databinding.ActivityMainBinding
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : AppCompatActivity(), KeypadAdapter.KeypadKeyListener,
                     OnClickListener {

    lateinit var adapter: KeypadAdapter

    private lateinit var binding: ActivityMainBinding

    lateinit var controller: CalculatorController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = CalculatorController()

        setUpNumberKeys()
        setUpOperationsListeners()
    }

    private fun setUpNumberKeys() {
        val listOfKeys = mutableListOf<Int>()
        for (i in -1..9) {
            listOfKeys.add(i)
        }

        listOfKeys.shuffle()

        adapter = KeypadAdapter(listOfKeys)
        adapter.keypadKeyListener = this

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = adapter

        binding.btnHistory.setOnClickListener {
            HistoryActivity.start(this@MainActivity, controller.computationHistory)
        }
    }

    private fun setUpOperationsListeners() {
        binding.apply {
            btnAdd.setOnClickListener(this@MainActivity)
            btnSubtract.setOnClickListener(this@MainActivity)
            btnMultiply.setOnClickListener(this@MainActivity)
            btnDivide.setOnClickListener(this@MainActivity)
            btnClear.setOnClickListener(this@MainActivity)
            btnEquals.setOnClickListener(this@MainActivity)
        }
    }

    override fun onKeypadPressed(key: Int) {
        val keyString: String = if (key == -1) {
            "."
        } else {
            key.toString()
        }

        var currentText = binding.tvDisplay.text.toString()
        if (currentText == "0") {
            currentText = ""
        }
        binding.tvDisplay.text =currentText + keyString
    }

    override fun onClick(view: View?) {
        try {
            view?.let {
                when (view.id) {
                    R.id.btnAdd -> {
                        controller.setOperation(
                            binding.tvDisplay.text.toString(),
                            CalculatorController.Companion.Operation.ADD
                        )
                        binding.tvDisplay.text ="0"
                    }
                    R.id.btnSubtract -> {
                        controller.setOperation(
                            binding.tvDisplay.text.toString(),
                            CalculatorController.Companion.Operation.SUB
                        )
                        binding.tvDisplay.text ="0"
                    }
                    R.id.btnMultiply -> {
                        controller.setOperation(
                            binding.tvDisplay.text.toString(),
                            CalculatorController.Companion.Operation.MUL
                        )
                        binding.tvDisplay.text ="0"
                    }
                    R.id.btnDivide -> {
                        controller.setOperation(
                            binding.tvDisplay.text.toString(),
                            CalculatorController.Companion.Operation.DIV
                        )
                        binding.tvDisplay.text ="0"
                    }
                    R.id.btnEquals -> {
                        val result = controller.evaluate(binding.tvDisplay.text.toString())
                        displayResult(result)
                    }
                    R.id.btnClear -> {
                        controller.clearEquation()
                        binding.tvDisplay.text ="0"
                    }
                    else -> {}
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun displayResult(result: Float?) {
        result?.let {
            binding.tvDisplay.text = if (ceil(it) == floor(it)) {
                it.toInt().toString()
            } else {
                it.toString()
            }
        }
    }
}