package com.arash.altafi.sample_flow_3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.arash.altafi.sample_flow_3.MainState.WatchState
import com.arash.altafi.sample_flow_3.databinding.ActivityMainBinding
import com.arash.altafi.sample_flow_3.stateSharedLiveData.StateSharedLiveDataActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainVM>()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnStateSharedLiveData.setOnClickListener {
            startActivity(Intent(this, StateSharedLiveDataActivity::class.java))
        }

        viewModel.stateFlow.collectIn(this) { render(it) }

        actionFlow()
            .onEach { viewModel.process(it) }
            .launchIn(lifecycleScope)
    }

    private fun actionFlow(): Flow<MainAction> {
        return merge(
            binding.buttonStart.clicks().map { MainAction.START },
            binding.buttonPause.clicks().map { MainAction.PAUSE },
            binding.buttonReset.clicks().map { MainAction.RESET },
        )
    }

    @SuppressLint("SetTextI18n")
    private fun render(state: MainState) {
        val mm = (state.seconds / 60).toString().padStart(2, '0')
        val ss = (state.seconds % 60).toString().padStart(2, '0')
        binding.textView.text = "$mm:$ss"

        when (state.watchState) {
            WatchState.RUNNING -> {
                binding.buttonStart.run {
                    isEnabled = false
                    text = "START"
                }
                binding.buttonPause.isEnabled = true
                binding.buttonReset.isEnabled = true
            }
            WatchState.PAUSED -> {
                binding.buttonStart.run {
                    isEnabled = true
                    text = "RESUME"
                }
                binding.buttonPause.isEnabled = false
                binding.buttonReset.isEnabled = true
            }
            WatchState.IDLE -> {
                binding.buttonStart.run {
                    isEnabled = true
                    text = "START"
                }
                binding.buttonPause.isEnabled = false
                binding.buttonReset.isEnabled = false
            }
        }
    }
}