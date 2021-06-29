package com.example.come

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val imgRlist: List<Int>,  private val listener : OnItemClickListener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = imgRlist[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(ItemsViewModel)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return imgRlist.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),View.OnClickListener {
            val imageView: ImageView = itemView.findViewById(R.id.imageview)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position:Int = adapterPosition
            listener.onItemClick(position)
        }
    }
    interface  OnItemClickListener {
        fun onItemClick(position: Int)
    }


    //definition for Onclick methods

}
