package com.jcdelacueva.bluefetchassignment1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryItem(
    val leftOperand: String,
    val rightOperand: String,
    val operation: String,
    val result: String
) : Parcelable