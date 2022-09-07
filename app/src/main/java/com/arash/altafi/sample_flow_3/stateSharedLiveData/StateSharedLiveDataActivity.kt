package com.arash.altafi.sample_flow_3.stateSharedLiveData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.arash.altafi.sample_flow_3.databinding.ActivityStateSharedLiveDataBinding
import kotlinx.coroutines.launch

class StateSharedLiveDataActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStateSharedLiveDataBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<StateSharedLiveDataViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            btnLiveData.setOnClickListener {
                viewModel.triggleLiveData()
            }
            btnSharedFlow.setOnClickListener {
                viewModel.triggleSharedFlow()
            }
            btnStateFlow.setOnClickListener {
                viewModel.triggleStateFlow()
            }
            btnFlow.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.triggleFlow().collect {
                        txtFlow.text = it
                    }
                }
            }
        }
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        viewModel.liveData.observe(this) {
            binding.txtLiveData.text = it
        }
        lifecycleScope.launchWhenCreated {
            viewModel.stateFlow.collect {
                binding.txtStateFlow.text = it
                Toast.makeText(this@StateSharedLiveDataActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.sharedFlow.collect {
                binding.txtSharedFlow.text = it
                Toast.makeText(this@StateSharedLiveDataActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

}