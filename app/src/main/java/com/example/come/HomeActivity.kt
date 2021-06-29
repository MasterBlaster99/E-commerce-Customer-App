package com.example.come

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.firebase.ui.auth.AuthUI

class HomeActivity : AppCompatActivity(), CustomAdapter.OnItemClickListener {

    private lateinit var list:ArrayList<Int>
    private lateinit var mAdapter:CustomAdapter
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var proAdapter:NoteAdapter
    private lateinit var allText:TextView
    private lateinit var pAdapter:PagerAdapter
    private lateinit var viewPager:ViewPager
    private lateinit var recyclerView:RecyclerView
    private lateinit var searchView:SearchView
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title="Shop"
        setSupportActionBar(toolbar)

        searchView=findViewById(R.id.searchView)

        list= ArrayList()
        list.add(R.drawable.ic_baseline_phone_android_24)
        list.add(R.drawable.ic_baseline_laptop_24)
        list.add(R.drawable.ic_baseline_work_outline_24)
        list.add(R.drawable.ic_baseline_weekend_24)
        list.add(R.drawable.ic_baseline_watch_24)
        list.add(R.drawable.ic_baseline_sports_cricket_24)
        list.add(R.drawable.ic_baseline_airport_shuttle_24)
        list.add(R.drawable.ic_baseline_menu_book_24)
        list.add(R.drawable.ic_baseline_kitchen_24)
        list.add(R.drawable.ic_baseline_select_all_24)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager=layoutManager
        mAdapter=CustomAdapter(list, this)
        recyclerView.adapter=mAdapter

        viewPager=findViewById(R.id.viewpager)
        pAdapter = PagerAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,10)
        viewPager.adapter=pAdapter

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item1 -> {
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                AuthUI.getInstance().signOut(this)
                true
            }
            R.id.item2 -> {
                val intent = Intent(applicationContext, AccountActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.item3 -> {
                val intent = Intent(applicationContext, MyOrdersActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.item4 -> {
                val intent = Intent(applicationContext, SavedItemsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.example_menu, menu)
        return true
    }

    override fun onItemClick(position: Int) {
         viewPager.setCurrentItem(position)
    }

}