package com.example.tomatoleafdiseaseclassificationapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityMainBinding
import com.example.tomatoleafdiseaseclassificationapp.databinding.HeaderNavigationDrawerBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File


class MainActivity : AppCompatActivity(), AuthStateListener{

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }

    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission(android.Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuLogOut -> {
                    Firebase.auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Snackbar.make(binding.root, "Successfully logged out", Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.menuLogin -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menuRegister -> {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

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

        binding.cardExplore.root.setOnClickListener {
            val intent = Intent(this, ExploreActivity::class.java)
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
            binding.navigationView.menu.removeItem(R.id.menuLogOut)
        } else {
            binding.cardHistory.root.visibility = View.VISIBLE
            binding.navigationView.menu.removeItem(R.id.menuLogin)
            binding.navigationView.menu.removeItem(R.id.menuRegister)
            val navHeaderBinding =
                HeaderNavigationDrawerBinding.bind(binding.navigationView.getHeaderView(0))
            navHeaderBinding.loggedUserHeader.text = currentUser.email
        }
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
            if(checkPermission(android.Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE))

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

        button.setOnClickListener {
            resultLauncher.launch("image/*")

        }
    }

    private fun checkPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(binding.root, "Camera Permission Granted", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "Camera Permission Denied, can't use camera feature. Grant permission from settings", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


}

