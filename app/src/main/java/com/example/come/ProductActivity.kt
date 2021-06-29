package com.example.come

import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ProductActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var title: TextView
    private lateinit var amount: TextView
    private lateinit var description: TextView
    private lateinit var specifications: TextView
    private lateinit var details: TextView
    private lateinit var orderBtn: Button
    private lateinit var saveItem: TextView
    var amountVal:String=""
    var uname : String=""
    var email : String=""
    var titleVal:String=""
    var desVal:String=""
    var specsVal:String=""
    var photoURLVal:String=""
    var detailsVal:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        title=findViewById(R.id.productTitle)
        saveItem = findViewById(R.id.SaveBtn)
        amount=findViewById(R.id.amountEditText)
        description=findViewById(R.id.descriptionTextView)
        specifications=findViewById(R.id.Specifications)
        details=findViewById(R.id.ProductDetails)
        imageView=findViewById(R.id.productImage)
        orderBtn=findViewById(R.id.OrderBtn)
        val bundle: Bundle? =intent.extras
        val id = bundle!!.get("UID")
        val colName = bundle!!.get("colName")
        var Firebaseauth: FirebaseAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        if (Firebaseauth.currentUser != null) {

            val docRef = db.collection(colName.toString()).document(id.toString())
            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        titleVal = document.get("title").toString()
                        amountVal = document.get("amount").toString()
                        photoURLVal = document.get("photoURL").toString()
                        desVal = document.get("des").toString()
                        specsVal = document.get("specs").toString()
                        detailsVal = document.get("details").toString()
                        amountVal = document.get("amount").toString()
                        photoURLVal = document.get("photoURL").toString()

                        title.setText(titleVal)
                        description.setText(desVal)
                        specifications.setText(specsVal)
                        details.setText(detailsVal)
                        amount.setText(amountVal + " Rs")
                        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/come-a1f3d.appspot.com/o/ProductImages%2F"+photoURLVal+ "?alt=media&token=b0f0de91-deb1-4d9c-81eb-a6559ed699e8).into(imageView").into(imageView)

                    } else {
                    }
                } else {
                }
            }
        }
        orderBtn.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(applicationContext,FinalOrderActivity::class.java)
            myIntent.putExtra("UID",id.toString())
            myIntent.putExtra("colName",colName.toString())
            startActivity(myIntent)
        })
        saveItem.setOnClickListener {
            val userID = FirebaseAuth.getInstance().uid
            val docRef = db.collection("users").document(userID.toString()).collection("savedItems").document()
            var map = HashMap<String,String>()
            map.put("title",titleVal)
            map.put("des",desVal)
            map.put("amount",amountVal)
            map.put("specs",specsVal)
            map.put("details",detailsVal)
            map.put("photoURL",photoURLVal)
            docRef.set(map)
            Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
        }
    }
}