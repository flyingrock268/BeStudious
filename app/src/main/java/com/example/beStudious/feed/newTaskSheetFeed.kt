package com.example.beStudious.feed

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.beStudious.R
import com.example.beStudious.TaskList.*
import com.example.beStudious.databinding.FragmentNewTaskFeedBinding
import com.example.beStudious.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

class newTaskSheetFeed(var feed: ArrayList<FeedItem>, var adp: FeedAdapter, var taskViewModel: TaskViewModel?, var parentPost: FeedItem?, var maxId: Int) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskFeedBinding
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


       binding = FragmentNewTaskFeedBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]

        binding.DeleteButton.setOnClickListener(){

            dismiss()

        }

        binding.SaveButton.setOnClickListener(){

            if(binding.name.text.toString() == ""){

                val toast = Toast.makeText(this.context, "Post needs a title", Toast.LENGTH_SHORT)
                toast.show()
                dismiss()

            }

            else{

                val newItem = FeedItem(
                    0,
                    binding.name.text.toString(),
                    binding.desc.text.toString(),
                    ArrayList(),

                )

                if(parentPost == null){

                    feed.add(feed.size,newItem)
                    taskViewModel!!.insertFeedItem(newItem)
                    dismiss()
                    adp.notifyItemInserted(feed.size - 1)

                    database = FirebaseDatabase.getInstance().getReference("feed")
//
//                    val snapshot = database.get().result
//                    var max: Int = 1
//
//                    if(snapshot != null){
//
//                        for (dataSnapShot in snapshot.children) {
//                            val post = dataSnapShot.getValue(FeedItem::class.java)
//                             if(post!!.id > max){
//
//                                 max = post.id
//
//                             }
//                        }
//
//                    }
                    newItem.id = maxId + 1
                    var temp = Int.MAX_VALUE - (maxId + 1)
                    database.child(temp.toString()).setValue(newItem)

                }

                else{

                    feed.add(feed.size,newItem)
                    newItem.id = maxId +1
                    dismiss()
                    adp.notifyItemInserted(feed.size)
                    var temp = Int.MAX_VALUE - (parentPost!!.id)
                    database = FirebaseDatabase.getInstance().getReference("feed")
                    database.child(temp.toString()).setValue(parentPost)

                }

            }

        }

    }

}