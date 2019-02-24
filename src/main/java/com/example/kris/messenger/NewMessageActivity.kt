package com.example.kris.messenger


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.kris.messenger.R.id.recycleView_newMessage
import com.example.kris.messenger.R.layout.user_row_new_message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.new_message_activity.*
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_message_activity)

        supportActionBar?.title = "Select User"

        // val adapter = GroupAdapter<ViewHolder>()
        //   adapter.add(UserItem())

        //   recycleView_newMessage.adapter = adapter
//fetch
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {

                val adapter = GroupAdapter<ViewHolder>()


                p0.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if(user!=null)
                    adapter.add(UserItem(user))
                }
                recycleView_newMessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })

    }
}


class UserItem(val user: User) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.Username.text = user.email
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView)

    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }


}

