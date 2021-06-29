package com.example.come

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SavedItemsActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var proAdapter:NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_items)
        var userID = FirebaseAuth.getInstance().uid.toString()

        var recyclerView2 = findViewById<RecyclerView>(R.id.savedItemsRecyclerView)
        var layoutManager2= LinearLayoutManager(applicationContext)
        recyclerView2.layoutManager=layoutManager2
        val query: Query = FirebaseFirestore.getInstance().collection("users").document(userID.toString()).collection("savedItems")
        val options = FirestoreRecyclerOptions.Builder<Example_Item>().setQuery(
            query,
            Example_Item::class.java
        ).build()
        proAdapter=NoteAdapter(options)
        recyclerView2.adapter=proAdapter
        proAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(documentSnapshot: DocumentSnapshot?, position: Int) {
                val myIntent = Intent(applicationContext,ProductActivity::class.java)
                var id = documentSnapshot!!.id
                myIntent.putExtra("UID",id)
                startActivity(myIntent)
            }
        })
    }
    override fun onStart() {
        super.onStart()
        proAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        proAdapter.stopListening()
    }
}