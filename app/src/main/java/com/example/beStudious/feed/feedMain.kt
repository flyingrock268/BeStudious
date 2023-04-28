package com.example.beStudious.feed

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beStudious.R
import androidx.recyclerview.widget.RecyclerView
import com.example.beStudious.BeStudious
import com.example.beStudious.Calendar.CalendarMain
import com.example.beStudious.Login
import com.example.beStudious.SettingsActivity
import com.example.beStudious.TaskList.*
import com.example.beStudious.Timer.TimerActivity
import com.example.beStudious.databinding.FeedMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class feedMain : AppCompatActivity(),FeedAdapter.OnItemClickListener {

    private lateinit var binding: FeedMainBinding
    private var exampleList = ArrayList<FeedItem>(100)
    private lateinit var database: DatabaseReference
    private var maxID: Int = 0
    private var user: FirebaseUser? = null
    lateinit var auth: FirebaseAuth
    private val taskViewModel: TaskViewModel by viewModels{

        TaskItemModelFactory((application as BeStudious).respository)

    }

    private val adapter = FeedAdapter(exampleList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FeedMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(false)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if(user == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish();
        }

        taskViewModel.FeedItems.observe(this@feedMain){

            for(post in it){

                taskViewModel.RemoveFeedItem(post)

            }

        }

        database = FirebaseDatabase.getInstance().getReference("feed")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {

                    for(i in 0 until exampleList.size){

                        adapter.notifyItemRemoved(0)
                        //taskViewModel.RemoveFeedItem(exampleList[i])

                    }

                    exampleList.clear()

                    for (dataSnapShot in snapshot.children) {
                        val post = dataSnapShot.getValue(FeedItem::class.java)
                        if(!exampleList.contains(post)) {

                            var check = false

                            taskViewModel.FeedItems.observe(this@feedMain){

                                for(post2 in it){

                                    if(post2.text1 == post!!.text1 && post2.text2 == post.text2){

                                        check = true

                                    }

                                    if(post2.id > maxID){

                                        maxID = post2.id

                                    }

                                }

                            }

                            if(!check){
                                exampleList.add(exampleList.size, post!!)
                                taskViewModel.insertFeedItem(post)
                                adapter.notifyItemInserted(exampleList.size -1)

                            }

                        }
                    }
                    //recyclerView.adapter = adapter


                }
            }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(this@feedMain, error.toString(), Toast.LENGTH_SHORT).show()
        }
        })
        //refresh()

        binding.newPostButton.setOnClickListener(){

            newTaskSheetFeed(exampleList, adapter,taskViewModel,null, maxID).show(supportFragmentManager, "NewTaskTag")


        }

        binding.refresh.setOnClickListener(){

            //insertItem()
            refresh()

        }

        setupNavBar()
    }

    fun refresh(){

        val newItem = FeedItem(
            0,
            "I love Bestudious",
            "I love this new app! It's so helpful",
            ArrayList(),

        )

        newItem.id = 0

        database.child(Int.MAX_VALUE.toString()).removeValue()
        database.child(Int.MAX_VALUE.toString()).setValue(newItem)

    }

    fun insertItem() {
//        val index = nextInt(3)
//        val newItem = FeedItem(
//            R.drawable.a,
//            "Title",
//            "I need help with Math 101",
//            ArrayList<FeedItem>()
//        )
//
//        exampleList.add(newItem)
//        adapter.notifyItemInserted(index)
    }

    override fun onItemClick(position: Int) {
        //Toast.makeText(this, " $position ", Toast.LENGTH_SHORT).show()
        //val clickedItem = exampleList[position]
        //clickedItem.text1 = " "

        if(exampleList[position].id == 0){

            return

        }

        showPost(exampleList,adapter,exampleList[position],supportFragmentManager,taskViewModel).show(supportFragmentManager, "NewTaskTag")
        adapter.notifyItemChanged(position)
    }

    private fun generateList(size: Int): ArrayList<FeedItem> {
        val list = ArrayList<FeedItem>()

//        for (i in 0 until size) {
//            val drawable = when (i % 3) {
//                0 -> R.drawable.a
//                1 -> R.drawable.b
//                3 -> R.drawable.c
//                4 -> R.drawable.d
//                5 -> R.drawable.e
//
//                else -> {R.drawable.e}
//            }
//
//            val item = FeedItem(drawable, "Title","I need help with Math 101")
//            list += item
//        }


        return list
    }

    fun setupNavBar(){

        //moves navBar up into place and fades it in
        ObjectAnimator.ofFloat(binding.navBar,"translationY", 250f,0f).apply{

            duration = 1000
            start()

        }
        ObjectAnimator.ofFloat(binding.navBar,"alpha", 0f,1f).apply{

            duration = 2000
            start()

        }


        //changes the activity to the taskList
        binding.tasksButton.setOnClickListener(){

            val intent = Intent(this, taskMain::class.java)
            startActivity(intent)

        }

        //changes the activity to the Calendar
        binding.CalendarButton.setOnClickListener(){

            val intent = Intent(this, CalendarMain::class.java)
            startActivity(intent)

        }

        binding.HomeButton.setOnClickListener{

            val intent = Intent(this, feedMain::class.java)
            startActivity(intent)

        }
        binding.TimeButton.setOnClickListener{
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

        binding.settingsButton.setOnClickListener{

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)

        }

    }

}

