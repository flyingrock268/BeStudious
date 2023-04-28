package com.example.beStudious.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beStudious.R
import com.example.beStudious.TaskList.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.beStudious.databinding.ShowPostBinding
import kotlin.random.Random

class showPost (var feed: ArrayList<FeedItem>, var adp: FeedAdapter, var post: FeedItem,var FM: FragmentManager, var tvm: TaskViewModel) : BottomSheetDialogFragment(),FeedAdapter.OnItemClickListener{

    private lateinit var binding: ShowPostBinding
    private val adapter = FeedAdapter(post.replies,this)
    private var maxID = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ShowPostBinding.inflate(layoutInflater)

        binding.repliesView.adapter = adapter
        binding.repliesView.layoutManager = LinearLayoutManager(this.context)
        binding.PostTitle.text = post.text1
        binding.PostContent.text = post.text2

        for(reply in post.replies){

            if(reply.id > maxID){

                maxID = reply.id

            }

        }

        binding.replyButton.setOnClickListener{

            newTaskSheetFeed(post.replies, adapter, tvm,post,maxID).show(FM, "NewTaskTag")
            refresh()

        }

        return binding.root

    }

    override fun onItemClick(position: Int) {
        //do nothing
    }
    fun refresh() {

        for (i in 0..post.replies.size) {

            adapter.notifyItemRemoved(i)

        }

        for (i in 0..post.replies.size) {
            adapter.notifyItemInserted(i)

        }
    }

}