package com.example.jangkau

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.jangkau.base.BaseActivity
import com.example.jangkau.databinding.ActivitySplashBinding
import com.example.jangkau.feature.transfer.TransferInputActivity
import com.example.jangkau.viewmodel.AuthViewModel
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity() {

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(
            layoutInflater
        )
    }
    private val authViewModel : AuthViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {    
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launchWhenStarted {
            authViewModel.isLoggedIn.collect { loggedIn ->
                if (loggedIn) {
                    binding.btnQris.visible()
                    binding.btnQris.setOnClickListener {
                        openQrisActivity()
                    }
                    binding.btnLogin.setOnClickListener {
                        openPinInputActivity(true)
                    }
                } else {
                    binding.btnQris.gone()
                    binding.btnLogin.setOnClickListener {
                        openLoginActivity()
                    }
                }
            }
        }

            // crash test button
//        val crashButton = Button(this)
//        crashButton.text = "Test Crash"
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash")
//        }
//
//        addContentView(crashButton, ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT))

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TransferInputActivity.PIN_INPUT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            openHomeActivity()
            finish()
        }
    }


}