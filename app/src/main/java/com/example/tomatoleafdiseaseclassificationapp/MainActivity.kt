package com.example.tomatoleafdiseaseclassificationapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityMainBinding
import com.example.tomatoleafdiseaseclassificationapp.databinding.HeaderNavigationDrawerBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import java.io.File


class MainActivity : AppCompatActivity(), AuthStateListener,
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.setNavigationItemSelectedListener(this)

        binding.cardHistory.cardImageView.setImageResource(R.drawable.history_icon)
        binding.cardHistory.cardTitle.text = getString(R.string.historyCardTitle)
        binding.cardHistory.cardDescr.text = getString(R.string.historyCardDescr)
        binding.cardExplore.cardImageView.setImageResource(R.drawable.explore_icon)
        binding.cardExplore.cardTitle.text = getString(R.string.exploreCardTitle)
        binding.cardExplore.cardDescr.text = getString(R.string.cardExploreDescr)

        binding.cardHistory.root.setOnClickListener {
            val intent = Intent(this, HistoryPageActivity::class.java)
            startActivity(intent)
        }

        val tempImageUri = initTempUri()

        registerTakePictureLauncher(tempImageUri)
        registerUploadPictureLauncher()



        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayoutMenu.open()
        }


        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayoutMenu.open()
        }

        updateUi(FirebaseAuth.getInstance().currentUser)

    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        updateUi(firebaseAuth.currentUser)
        if (firebaseAuth.currentUser == null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUi(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            binding.cardHistory.root.visibility = View.GONE
        } else {
            binding.cardHistory.root.visibility = View.VISIBLE
            val navHeaderBinding =
                HeaderNavigationDrawerBinding.bind(binding.navigationView.getHeaderView(0))
            navHeaderBinding.loggedUserHeader.text = currentUser.email
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_logout -> {
                Log.d("DRAWER", "LogOut")
            }
        }
        //close navigation drawer
        binding.drawerLayoutMenu.close()
        return true
    }

    private fun initTempUri(): Uri {
        //gets the temp_images dir
        val tempImagesDir = File(
            applicationContext.filesDir, //this function gets the external cache dir
            getString(R.string.temp_images_dir)
        ) //gets the directory for the temporary images dir

        tempImagesDir.mkdir() //Create the temp_images dir

        //Creates the temp_image.jpg file
        val tempImage = File(
            tempImagesDir, //prefix the new abstract path with the temporary images dir path
            getString(R.string.temp_image)
        ) //gets the abstract temp_image file name

        //Returns the Uri object to be used with ActivityResultLauncher
        return FileProvider.getUriForFile(
            applicationContext,
            getString(R.string.authorities),
            tempImage
        )
    }

    private fun registerTakePictureLauncher(path: Uri) {
        val button = binding.cameraIcon
        val resultLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            val intent = Intent(this, DiseaseInfoActivity::class.java)
            intent.putExtra("imagePath", path.toString())
            startActivity(intent)
        }
        button.setOnClickListener {
            resultLauncher.launch(path) //launches the activity here

        }
    }

    private fun registerUploadPictureLauncher() {
        val button = binding.galeryIcon
        val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            val intent = Intent(this, DiseaseInfoActivity::class.java)
            intent.putExtra("imagePath", it.toString())
            startActivity(intent)
        }

        //Launches the camera when button is pressed.
        button.setOnClickListener {
            resultLauncher.launch("image/*") //launches the activity here

        }
    }


}

