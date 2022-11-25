package com.jcdelacueva.bluefetchassignment1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KeypadAdapter(private val keypadNumbers: List<Int>) : RecyclerView.Adapter<KeypadAdapter.ViewHolder>() {

    interface KeypadKeyListener {
        fun onKeypadPressed(key: Int)
    }

    var keypadKeyListener: KeypadKeyListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keypad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val key = keypadNumbers[holder.adapterPosition]
        holder.bind(keypadNumbers[holder.adapterPosition])
        holder.itemView.setOnClickListener {
            keypadKeyListener?.onKeypadPressed(key)
        }
    }

    override fun getItemCount(): Int {
        return keypadNumbers.size
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(number: Int) {
            view.findViewById<TextView>(R.id.tvKeyButton).text = if (number == -1) "." else number.toString()
        }
    }
}