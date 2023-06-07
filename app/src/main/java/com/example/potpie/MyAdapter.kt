package com.example.potpie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(var con : Context , var list: List<UsersItem>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{

        fun onItemClickListener(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener = listener
    }

    inner class ViewHolder(listener: onItemClickListener,v : View) : RecyclerView.ViewHolder(v){


        var img = v.findViewById<ImageView>(R.id.profile_image)
        var tvName = v.findViewById<TextView>(R.id.LV_tv)
        var tvprice = v.findViewById<TextView>(R.id.LV_price)

        init {
            v.setOnClickListener{
                listener.onItemClickListener(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view = LayoutInflater.from(con).inflate(R.layout.list_item,parent,false)
        return ViewHolder(mlistener,view)
    }

    override fun getItemCount(): Int {
     return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       Glide.with(con).load(list[position].image).into(holder.img)
        holder.tvName.text = list[position].title
        holder.tvprice.text = list[position].price.toString()
    }
}