package com.example.tomatoleafdiseaseclassificationapp


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityMainBinding
import com.example.tomatoleafdiseaseclassificationapp.databinding.HeaderNavigationDrawerBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(), AuthStateListener, NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.setNavigationItemSelectedListener(this)

        binding.cardHistory.cardImageView.setImageResource(R.drawable.history_icon)
        binding.cardExplore.cardImageView.setImageResource(R.drawable.explore_icon)

        binding.cameraIcon.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayoutMenu.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            binding.drawerLayoutMenu.close()
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayoutMenu.open()
        }

        updateUi(FirebaseAuth.getInstance().currentUser)

    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        updateUi(firebaseAuth.currentUser)
    }

    private fun updateUi(currentUser : FirebaseUser?) {
        if (currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val navHeaderBinding =
                HeaderNavigationDrawerBinding.bind(binding.navigationView.getHeaderView(0))
            navHeaderBinding.loggedUserHeader.text = currentUser.email
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            1-> Firebase.auth.signOut()
        }
        //close navigation drawer
        //close navigation drawer
        binding.drawerLayoutMenu.close()
        return true
    }
}