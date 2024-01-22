package com.kyutae.jackpotluck

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumberAdapter(private val lottoLists: List<List<Int>>) : RecyclerView.Adapter<NumberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return NumberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val lottoList = lottoLists[position]
        // 각 숫자를 TextView에 설정
        holder.textView1.text = lottoList[0].toString()
        holder.textView2.text = lottoList[1].toString()
        holder.textView3.text = lottoList[2].toString()
        holder.textView4.text = lottoList[3].toString()
        holder.textView5.text = lottoList[4].toString()
        holder.textView6.text = lottoList[5].toString()
//        holder.textView.text = "리스트 ${position + 1}: $lottoList"




        // 각 숫자에 대한 색상 설정
        val context = holder.itemView.context

        setNumberColor(context, holder.textView1, lottoList[0])
        setNumberColor(context, holder.textView2, lottoList[1])
        setNumberColor(context, holder.textView3, lottoList[2])
        setNumberColor(context, holder.textView4, lottoList[3])
        setNumberColor(context, holder.textView5, lottoList[4])
        setNumberColor(context, holder.textView6, lottoList[5])
    }
    private fun setNumberColor(context: Context, textView: TextView, number: Int) {
        val colorResId = when {
            number <= 10 -> R.drawable.circle_yellow
            number <= 20 -> R.drawable.circle_blue
            number <= 30 -> R.drawable.circle_green
            number <= 40 -> R.drawable.circle_gray
            else -> R.drawable.circle_red
        }

        textView.setBackgroundResource(colorResId)

    }
    override fun getItemCount(): Int {
        return lottoLists.size
    }
}
