package com.example.tomatoleafdiseaseclassificationapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.registerButton.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val pass = binding.editTextPassword.text.toString()
            val confirmPass = binding.editTextConfirmPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Snackbar.make(binding.root, it.exception?.message.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Snackbar.make(binding.root, "Password is not matching", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(binding.root, "Error: empty fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}