package com.example.tomatoleafdiseaseclassificationapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomatoleafdiseaseclassificationapp.adapters.HistoryCardAdapter
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityHistoryPageBinding
import com.example.tomatoleafdiseaseclassificationapp.models.HistoryCardModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HistoryPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryPageBinding
    private var cardModelArrayList: ArrayList<HistoryCardModel> = ArrayList()
    private lateinit var rvAdapter: HistoryCardAdapter
    private var db = FirebaseFirestore.getInstance()
    private var user = FirebaseAuth.getInstance().currentUser
    private var userId = user?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val cardAdapter = HistoryCardAdapter(cardModelArrayList)
        binding.recyclerView.adapter = cardAdapter
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)

        binding.recyclerView.layoutManager = mLayoutManager
        readData()
    }

    private fun readData() {
        db.collection("Users/$userId/history").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (!queryDocumentSnapshots.isEmpty) {
                    val list = queryDocumentSnapshots.documents
                    for (item in list) {
                        val card = HistoryCardModel(
                            item.data?.get("disease_name") as String,
                            item.data?.get("date") as Timestamp
                        )
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

    private fun removeItemDatabase(docId: String){
        db.collection("Users/$userId/Cards").document(docId).delete()
    }
}