package com.example.come

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FurnitureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FurnitureFragment : Fragment() {
    private lateinit var adapter:NoteAdapter
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_furniture, container, false)
        var recyclerView2 = view.findViewById<RecyclerView>(R.id.furnitureRecyclerView)
        var layoutManager2= LinearLayoutManager(view.context)
        recyclerView2.layoutManager=layoutManager2
        val query: Query = FirebaseFirestore.getInstance().collection("furniture")
        val options = FirestoreRecyclerOptions.Builder<Example_Item>().setQuery(
            query,
            Example_Item::class.java
        ).build()
        adapter=NoteAdapter(options)
        recyclerView2.adapter=adapter
        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(documentSnapshot: DocumentSnapshot?, position: Int) {
                val myIntent = Intent(view.context,ProductActivity::class.java)
                var id = documentSnapshot!!.id
                myIntent.putExtra("UID",id)
                myIntent.putExtra("colName","furniture")
                startActivity(myIntent)
            }
        })
        return view
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
    fun FurnitureFragment(){

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FurnitureFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FurnitureFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}