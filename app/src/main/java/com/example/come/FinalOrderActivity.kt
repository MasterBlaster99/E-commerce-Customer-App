package com.example.come

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.JsonObject
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.squareup.picasso.Picasso
import org.json.JSONObject

class FinalOrderActivity : AppCompatActivity() , PaymentResultListener{
    private lateinit var imageView: ImageView
    private lateinit var title: TextView
    private lateinit var amount: TextView
    private lateinit var address:EditText
    private lateinit var phone:EditText
    private var category : String = ""
    private lateinit var selectPaymentBtn:Button
    private lateinit var orderBtn:Button
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
        setContentView(R.layout.activity_final_order)
        imageView = findViewById(R.id.imageview)
        title = findViewById(R.id.text)
        amount = findViewById(R.id.amountText)
        address = findViewById(R.id.addressEditText)
        phone = findViewById(R.id.NumberEditText)
        selectPaymentBtn = findViewById(R.id.choosePaymentMethodBtn)
        val bundle: Bundle? = intent.extras
        val id = bundle!!.get("UID")
        val colName = bundle!!.get("colName")
        var Firebaseauth: FirebaseAuth = FirebaseAuth.getInstance()

        orderBtn=findViewById(R.id.confirmOrder)
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
                        amount.setText(amountVal)
                        Picasso.get()
                            .load("https://firebasestorage.googleapis.com/v0/b/come-a1f3d.appspot.com/o/ProductImages%2F" + photoURLVal + "?alt=media&token=b0f0de91-deb1-4d9c-81eb-a6559ed699e8).into(imageView")
                            .into(imageView)
                    } else {
                    }
                } else {
                }
            }
        }
        if (Firebaseauth.currentUser != null) {
            var userID = Firebaseauth.uid.toString()
            val docRef = db.collection("users").document(userID)
            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        address.setText(document.get("address").toString())
                        phone.setText(document.get("phone").toString())
                        uname=document.get("name").toString()
                        email=document.get("name").toString()
                    } else {
                    }
                } else {
                }
            }
        }
        selectPaymentBtn.setOnClickListener {
            val popup = PopupMenu(applicationContext, selectPaymentBtn)
            popup.menuInflater.inflate(R.menu.popup_menu_payments, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.item1 -> {
                        selectPaymentBtn.setText("Online Payment")
                        category = "OnlinePayment"
                    }
                    R.id.item2 -> {
                        selectPaymentBtn.setText("Cash On Delivery")
                        category = "CashOnDelivery"
                    }
                }
                false
            })
        }
        orderBtn.setOnClickListener(View.OnClickListener {

            if(category.equals("OnlinePayment")) {
                var amnt = amount.text.toString()
                var checkout = Checkout()
                checkout.setKeyID("rzp_test_9CJZMqm5ml1Oel")
                checkout.setImage(R.drawable.ic_baseline_kitchen_24)
                var jsonObject = JSONObject()
                jsonObject.put("name", "shekhar")
                jsonObject.put("description", "payment")
                jsonObject.put("theme.color", R.color.green)
                jsonObject.put("currency", "INR")
                jsonObject.put("amount", amnt.toDouble() * 100)
                jsonObject.put("prefill.contact", phone.text.toString())
                jsonObject.put("prefill.email", email)
                checkout.open(this, jsonObject)
            }
            else if(category.equals("CashOnDelivery")){

                var arrayList:ArrayList<String>
                val userID = Firebaseauth.uid
                val docRefProduct = db.collection("All").document(id.toString())
                docRefProduct.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document!!.exists()) {

                        } else {
                        }
                    } else {
                    }
                }
                val docRef = db.collection("users").document(userID.toString()).collection("MyOrder").document()
                var map = HashMap<String,String>()
                map.put("title",titleVal)
                map.put("des",desVal)
                map.put("amount",amountVal)
                map.put("specs",specsVal)
                map.put("details",detailsVal)
                map.put("photoURL",photoURLVal)
                docRef.set(map)
                val intent = Intent(applicationContext,OrderPlacedActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext,"Please select a payment methods",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onPaymentSuccess(p0: String?) {
        var dialogue = AlertDialog.Builder(this)
        dialogue.setTitle("Payment ID")
        dialogue.setMessage(p0)
        dialogue.show()
        val intent = Intent(applicationContext,OrderPlacedActivity::class.java)
        startActivity(intent)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(applicationContext,p1,Toast.LENGTH_LONG).show()
    }
}