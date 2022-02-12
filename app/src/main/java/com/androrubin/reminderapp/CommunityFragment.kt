package com.androrubin.reminderapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CommunityFragment : Fragment() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Community>
    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageId = arrayOf(
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,
            R.drawable.g
        )

        heading = arrayOf(
            "Community 1",
            "Community 2",
            "Community 3",
            "Community 4",
            "Community 5",
            "Community 6",
            "Community 7"

        )

    }

    private fun getUserdata() {
        for (i in imageId.indices){
            val community  = Community(imageId[i], heading[i])
            newArrayList.add(community)
        }

        newRecyclerView.adapter = CommunityAdapter(newArrayList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)
        // Inflate the layout for this fragment
        newRecyclerView = view.findViewById(R.id.recylerView1)
        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Community>()
        getUserdata()
        return view
    }


}