package com.example.jangkau

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jangkau.base.BaseActivity
import com.example.jangkau.databinding.ActivityErrorBinding
import com.example.jangkau.feature.home.HomeActivity
import com.example.jangkau.viewmodel.AuthViewModel
import org.koin.android.ext.android.inject

class ErrorActivity : BaseActivity() {

    private lateinit var binding: ActivityErrorBinding
    private val authViewModel : AuthViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val errorMessage = intent.getStringExtra("ERROR_MESSAGE")
        binding.tvErrorMessage.text = errorMessage

        binding.btnRefresh.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        binding.btnLogout.setOnClickListener {
            authViewModel.logout()
            openLoginActivity()
        }
    }
}
