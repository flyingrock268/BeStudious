package com.example.beStudious.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.beStudious.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FeedAdapter(
    private val exampleList: List<FeedItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<FeedAdapter.FeedItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.feed_item,
            parent, false
        )

        return FeedItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        val currentItem = exampleList[position]

        //holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2
    }


    override fun getItemCount() = exampleList.size

    inner class FeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        //val imageView: ImageView
        val textView1: TextView
        val textView2: TextView

        init {
            //imageView = itemView.findViewById(R.id.image)
            textView1 = itemView.findViewById(R.id.text1)
            textView2 = itemView.findViewById(R.id.text2)
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
