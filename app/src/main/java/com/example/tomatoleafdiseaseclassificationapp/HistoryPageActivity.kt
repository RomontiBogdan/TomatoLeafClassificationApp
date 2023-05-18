package com.example.tomatoleafdiseaseclassificationapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomatoleafdiseaseclassificationapp.adapters.HistoryCardAdapter
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityHistoryPageBinding
import com.example.tomatoleafdiseaseclassificationapp.models.HistoryCardModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat


class HistoryPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryPageBinding
    private var cardModelArrayList: ArrayList<HistoryCardModel> = ArrayList()
    private var db = FirebaseFirestore.getInstance()
    private var user = FirebaseAuth.getInstance().currentUser
    private var userId = user?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardAdapter = HistoryCardAdapter(cardModelArrayList)
        cardAdapter.setOnRatingBarChangeListener(object :
            HistoryCardAdapter.OnRatingBarChangeListener {
            override fun onRatingBarChange(rating: Float, position: Int) {
                addRatingToScan(cardAdapter.getId(position), rating.toLong())
            }

            override fun onItemClick(position: Int) {
                showTreatmentDialog(cardAdapter.getDisease(position) ,cardAdapter.getTreatment(position))
            }

        })
        binding.recyclerView.adapter = cardAdapter
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)

        binding.recyclerView.layoutManager = mLayoutManager
        readData()
    }

    private fun showTreatmentDialog(diseaseName: String, treatment: String) {
        db.document("Treatments/Tomato/Diseases/$diseaseName")
            .get()
            .addOnSuccessListener {
                val treatmentDescription = it.get("$treatment").toString()
                MaterialAlertDialogBuilder(this)
                    .setTitle("Disease info")
                    .setMessage(treatmentDescription)
                    .setPositiveButton( "Ok"){ _, _-> }
                    .show()
            }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addRatingToScan(docId: String, rating: Long) {
        db.collection("Users/$userId/history").document(docId).update("rating", rating)
        binding.recyclerView.adapter?.notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat")
    private fun readData() {
        db.collection("Users/$userId/history").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    val list = queryDocumentSnapshots.documents
                    for (item in list) {
                        val diseaseName = item.data?.get("disease_name") as String
                        val treatmentUsed = item.data?.get("treatment") as String
                        val timestamp = item.data?.get("date") as Timestamp
                        val sdf = SimpleDateFormat("dd/MM/yyyy")
                        val date = sdf.format(timestamp.toDate()).toString()
                        val card = HistoryCardModel(diseaseName, treatmentUsed, date, item.id)
                        if (item.data?.containsKey("rating") == true) {
                            val rating = item.data?.get("rating") as Long
                            card.rating = rating.toInt()
                        }

                        cardModelArrayList.add(card)
                    }
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this,
                        "No data found in Database",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Log.d("HISTORYVIEW", it.message.toString())
                Toast.makeText(this, "Fail to get the data.", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}