package com.example.tomatoleafdiseaseclassificationapp.models

import android.graphics.Bitmap
import com.google.firebase.Timestamp
import java.util.Date

class HistoryCardModel(val diseaseName: String, val date: String, val id : String, var rating: Int = 0)