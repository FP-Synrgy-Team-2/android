package com.example.jangkau.feature

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.CodeScanner
import com.example.jangkau.R
import com.example.jangkau.base.BaseActivity
import com.example.jangkau.databinding.ActivityScanQractivityBinding

class ScanQRActivity : BaseActivity() {

    private lateinit var binding : ActivityScanQractivityBinding
    private lateinit var codeScanner: CodeScanner
    private var isFlashEnabled = false
    private val cameraRequestCode = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        codeScanner = CodeScanner(this, binding.scannerView)

        // Initialize CodeScanner settings
        codeScanner.apply {

        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFlash.setOnClickListener {
            isFlashEnabled = !isFlashEnabled
            codeScanner.isFlashEnabled = isFlashEnabled
            updateFlashButtonIcon()
        }
        checkPermissions()
    }

    private fun updateFlashButtonIcon() {
        val flashIcon = if (isFlashEnabled) {
            R.drawable.ic_flash_enabled
        } else {
            R.drawable.ic_flash_disabled
        }
        binding.btnFlash.setImageResource(flashIcon)
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), cameraRequestCode)
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                finish()
            }
        }
    }

    private fun startCamera() {
        codeScanner.startPreview()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}