package com.example.tomatoleafdiseaseclassificationapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityForgotPassBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ForgotPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPassBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.forgotpassButton.setOnClickListener {
            val email = binding.editTextEmailForgotPass.text.toString()
            if (email.isNotEmpty()) {
                firebaseAuth.setLanguageCode("en")
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, LoginActivity::class.java)
                        Snackbar.make(binding.root, "Email successfully sent", Snackbar.LENGTH_SHORT).show()
                        startActivity(intent)
                    } else {
                        Snackbar.make(binding.root, it.exception?.message.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                }
            } else {
                Snackbar.make(binding.root, "Error: empty fields", Snackbar.LENGTH_SHORT).show()

            }
        }

        binding.textViewToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.textViewToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}