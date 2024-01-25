package com.kyutae.jackpotluck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView1: TextView = itemView.findViewById(R.id.circle1)
    val textView2: TextView = itemView.findViewById(R.id.circle2)
    val textView3: TextView = itemView.findViewById(R.id.circle3)
    val textView4: TextView = itemView.findViewById(R.id.circle4)
    val textView5: TextView = itemView.findViewById(R.id.circle5)
    val textView6: TextView = itemView.findViewById(R.id.circle6)
    val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
}
