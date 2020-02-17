package com.sammomichael.retroroomco.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sammomichael.retroroomco.R
import com.sammomichael.retroroomco.data.userdata.User
import kotlinx.android.synthetic.main.user_itemview.view.*

class UserRecyclerViewAdapter(private val data: List<User>) :
    Adapter<UserRecyclerViewAdapter.UserViewHolder>() {
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_itemview, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.textView2.text = data[position].name
        holder.itemView.textView4.text = data[position].body
        holder.itemView.textView5.text = data[position].email
        holder.itemView.textView6.text = data[position].id.toString()
    }

}