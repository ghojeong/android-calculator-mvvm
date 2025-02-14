package edu.nextstep.camp.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import edu.nextstep.camp.calculator.databinding.ActivityCalculatorBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding
    private val viewModel: CalculatorViewModel by viewModels()
    private val memoriesAdapter: MemoriesAdapter by lazy { MemoriesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = memoriesAdapter

        setupCalculationError()
        setupMemories()
    }

    private fun toastCalculationError() {
        Toast.makeText(this, R.string.incomplete_expression, Toast.LENGTH_SHORT).show()
    }

    private fun setupCalculationError(): Job = lifecycleScope.launch {
        viewModel.onCalculationErrorEvent.collect {
            toastCalculationError()
        }
    }

    private fun setupMemories(): Job = lifecycleScope.launch {
        viewModel.memories.collect {
            memoriesAdapter.submitList(it)
        }
    }
}
