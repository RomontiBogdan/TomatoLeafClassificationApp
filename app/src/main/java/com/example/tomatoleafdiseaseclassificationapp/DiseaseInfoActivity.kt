package com.example.tomatoleafdiseaseclassificationapp

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.example.tomatoleafdiseaseclassificationapp.adapters.TabPagerAdapter
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityDiseaseInfoBinding
import com.example.tomatoleafdiseaseclassificationapp.ml.LeafResNet50
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.lang.IllegalArgumentException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.min


class DiseaseInfoActivity : AppCompatActivity() {
    private val imageSize = 224
    private lateinit var binding: ActivityDiseaseInfoBinding
    private lateinit var treatmentArrowButton: ImageButton
    private lateinit var symptomsArrowButton: ImageButton
    private lateinit var causesArrowButton: ImageButton
    private lateinit var treatmentHiddenView: LinearLayout
    private lateinit var symptomsHiddenView: LinearLayout
    private lateinit var causesHiddenView: LinearLayout
    private lateinit var treatmentCardview: CardView
    private lateinit var symptomsCardview: CardView
    private lateinit var causesCardview: CardView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private var db = FirebaseFirestore.getInstance()
    private var user = FirebaseAuth.getInstance().currentUser
    private var userId = user?.uid

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runOnUiThread {
            val intent = intent
            val imagePath = intent.getStringExtra("imagePath")
            val fileUri = Uri.parse(imagePath)
            val source = ImageDecoder.createSource(contentResolver, fileUri)
            val bitmap = ImageDecoder.decodeBitmap(source)
            val dimension = min(bitmap.width, bitmap.height)
            val thumbnail = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension)

            val bitmapImage = Bitmap.createScaledBitmap(thumbnail, imageSize, imageSize, false)
            val softwareBitmap: Bitmap = bitmapImage.copy(Bitmap.Config.ARGB_8888, false)
            val result = classifyImage(softwareBitmap)

            binding.imageView.setImageBitmap(thumbnail)
            binding.diseaseTitle.text = result

            db.document("Treatments/Tomato/Diseases/$result")
                .get()
                .addOnSuccessListener {
                    binding.causesDescr.text = it.get("Causes").toString()
                    binding.symptomsDescr.text = it.get("Symptoms").toString()
                }
                .addOnFailureListener { exception ->

                }
            if(userId != null)
                addToHistory(result)
        }

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

        tabLayout.addTab(tabLayout.newTab().setText("Conventional"))
        tabLayout.addTab(tabLayout.newTab().setText("Bio"))
        tabLayout.addTab(tabLayout.newTab().setText("Organic"))

        val fragmentManager = supportFragmentManager
        val adapter = TabPagerAdapter(fragmentManager, lifecycle)
        viewPager2.adapter = adapter
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        treatmentCardview = binding.treatmentCardview
        treatmentArrowButton = binding.treatmentArrowButton
        treatmentHiddenView = binding.treatmentHiddenView

        symptomsCardview = binding.symptomsCardview
        symptomsArrowButton = binding.symptomsArrowButton
        symptomsHiddenView = binding.symptomsHiddenView

        causesCardview = binding.causesCardview
        causesArrowButton = binding.causesArrowButton
        causesHiddenView = binding.causesHiddenView

        set_expandable(treatmentArrowButton, treatmentHiddenView, treatmentCardview)
        set_expandable(symptomsArrowButton, symptomsHiddenView, symptomsCardview)
        set_expandable(causesArrowButton, causesHiddenView, causesCardview)



    }

    private fun set_expandable(arrow : ImageButton, hiddenView : LinearLayout, cardView : CardView) {
        arrow.setOnClickListener {
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            Log.d("EXPANDABLE","set_expandable() started")
            if (hiddenView.visibility == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenView.visibility = View.GONE
                arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenView.visibility = View.VISIBLE
                arrow.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }
    }

    private fun classifyImage(image: Bitmap): String {
        try {
            val model: LeafResNet50 = LeafResNet50.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: LeafResNet50.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "Bacterial spot",
                "Early blight",
                "Healthy",
                "Late blight",
                "Leaf mold",
                "Septoria leaf spot",
                "Spider mites",
                "Target spot",
                "Mosaic virus",
                "Yellow leaf curl virus"
            )
            val result = classes[maxPos]

            model.close()

            return result
        } catch (e: IOException) {
            // TODO Handle the exception
        }

        return null.toString()
    }

    private fun addToHistory(disease : String){
        if (userId != null) {
            val scannedDisease = hashMapOf(
                "date" to FieldValue.serverTimestamp(),
                "disease_name" to disease,
                "rating" to 0
            )
            Firebase.firestore.collection("Users/$userId/history")
                .add(scannedDisease).addOnSuccessListener {
                }
        } else throw IllegalArgumentException("")
    }



}