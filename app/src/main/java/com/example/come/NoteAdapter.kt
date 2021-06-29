  package com.example.come

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.squareup.picasso.Picasso

internal class NoteAdapter(options: FirestoreRecyclerOptions<Example_Item?>) : FirestoreRecyclerAdapter<Example_Item?, NoteAdapter.NoteHolder?>(options) {
    private var listener: OnItemClickListener? = null
    override fun onBindViewHolder(holder: NoteHolder, position: Int, model: Example_Item) {
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/come-a1f3d.appspot.com/o/ProductImages%2F"+model.getphotoURL()+ "?alt=media&token=b0f0de91-deb1-4d9c-81eb-a6559ed699e8").into(holder.imageView)
        holder.productName.setText(model.gettitle())
        holder.amount.setText(model.getamount()+" Rs")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row,
                parent, false)
        return NoteHolder(v)
    }

    internal inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var imageView:ImageView
        lateinit var productName:TextView
        lateinit var amount:TextView


        init {
            imageView=itemView.findViewById(R.id.displayImage)
            productName=itemView.findViewById(R.id.textDisplayName)
            amount=itemView.findViewById(R.id.amount)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener!!.onItemClick(getSnapshots().getSnapshot(position), position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(documentSnapshot: DocumentSnapshot?, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}