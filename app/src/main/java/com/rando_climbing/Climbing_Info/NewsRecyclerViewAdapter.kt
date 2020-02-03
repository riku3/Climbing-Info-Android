package com.rando_climbing.Climbing_Info

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso

class NewsRecyclerViewAdapter(private val articles: List<Article>, private val listener: ListListener) : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemView = LayoutInflater.from(parent.context).inflate(R.layout.article, parent, false)
        return ViewHolder(listItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listItem = articles[position]

        holder.title.setText(listItem.title)
        holder.site_name.setText(listItem.site_name)
        holder.itemView.setOnClickListener {
            listener.onClickListItem(it, articles[position])
        }

        Picasso.get()
                .load(listItem.image)
                .placeholder(R.drawable.user_placeholder)
                .into(holder.Image)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    interface ListListener {
        fun onClickListItem(tappedView: View, article: Article)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var site_name: TextView
        var Image: ImageView
        var Layout: LinearLayout

        init {

            title = itemView.findViewById(R.id.title)
            site_name = itemView.findViewById(R.id.site_name)
            Image = itemView.findViewById(R.id.imageview)
            Layout = itemView.findViewById(R.id.linear_layout)
        }
    }
}