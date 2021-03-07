package com.example.cbslassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cbslassignment.model.musiclistmodel
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.gson.Gson
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class videoinfo : AppCompatActivity() {

    var imageview :ImageView?=null;
    private lateinit var textview: TextView


    var model :musiclistmodel?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoinfo)
        setTitle("Video info");
        imageview=findViewById(R.id.imageview)
        textview=findViewById(R.id.textview)

        model= Gson().fromJson(intent.getStringExtra("data"),musiclistmodel::class.java)


        Picasso.with(this)
            .load(model?.artworkUrl100)
            .placeholder(R.drawable.loadimage)
            .into(imageview, object : Callback {
                override fun onSuccess() {
                }
                override fun onError() {
                }
            })
        textview.text=  model?.trackId.toString() +"\n"+model?.trackName +"\n"+model?.artistName+"\n"+model?.trackPrice+"\n"+model?.kind
    }
}