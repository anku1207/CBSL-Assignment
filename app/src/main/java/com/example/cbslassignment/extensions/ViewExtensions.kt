package com.example.cbslassignment.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

fun Context.showToast(msg : String, long : Boolean){
        Toast.makeText(this, msg, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

fun View.setGone(){
    this.visibility = View.GONE
}

fun ImageView.loadImageUrl(imageUrl : String){
    Picasso.with(this.context)
            .load(imageUrl)
            .into(this)
}