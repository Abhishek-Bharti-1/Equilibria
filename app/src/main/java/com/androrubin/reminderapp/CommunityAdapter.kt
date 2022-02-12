package com.androrubin.reminderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class CommunityAdapter(private val communityList: ArrayList<Community>) :
    RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.community_list,
        parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentitem = communityList[position]
        holder.titleImage.setImageResource(currentitem.titleImage)
        holder.heading.text = currentitem.heading
    }

    override fun getItemCount(): Int {
        return communityList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val titleImage = itemView.findViewById<ImageView>(R.id.com_prof_pic)
        val heading = itemView.findViewById<TextView>(R.id.com_name)

    }
}