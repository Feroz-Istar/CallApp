package com.call.feroz.callapp.adapter

/**
 * Created by Feroz on 14-03-2018.
 */
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.call.feroz.callapp.R
import com.call.feroz.callapp.pojo.Contact
import kotlinx.android.synthetic.main.list_layout.view.*

/**
 * Created by Belal on 6/19/2017.
 */

class CustomAdapter(val userList: ArrayList<Contact>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }



    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: Contact) {

            itemView.textViewUsername.text= user.display_name
            itemView.textViewAddress.text = user.phone_number
        }
    }
}