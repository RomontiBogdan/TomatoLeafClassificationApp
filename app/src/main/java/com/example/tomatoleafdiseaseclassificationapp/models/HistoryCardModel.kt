package com.example.tomatoleafdiseaseclassificationapp.models

import android.graphics.Bitmap
import com.google.firebase.Timestamp

class HistoryCardModel(val diseaseName: String, val date: Timestamp, val rating: Int = 0, val image: Bitmap? = null) {
}