package com.example.tomatoleafdiseaseclassificationapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomatoleafdiseaseclassificationapp.adapters.ExploreCardAdapter
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityExploreBinding
import com.google.firebase.firestore.FirebaseFirestore

class ExploreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExploreBinding
    private var cardExploreArrayList: ArrayList<String> = ArrayList()
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardAdapter = ExploreCardAdapter(cardExploreArrayList)
        cardAdapter.setOnItemClickListener(object :
            ExploreCardAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                startDiseaseInfoActivity(cardAdapter.getDisease(position))
            }

        })
        binding.exploreRecyclerView.adapter = cardAdapter
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.exploreRecyclerView.layoutManager = mLayoutManager
        readData()
    }

    private fun startDiseaseInfoActivity(diseaseName: String) {
        val intent = Intent(this, DiseaseInfoActivity::class.java)
        intent.putExtra("diseaseName", diseaseName)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun readData() {
        db.collection("Treatments/Tomato/Diseases").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    val list = queryDocumentSnapshots.documents
                    for (item in list) {
                        val diseaseName = item.id
                        cardExploreArrayList.add(diseaseName)
                    }
                    binding.exploreRecyclerView.adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this,
                        "No data found in Database",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}