package com.example.cbslassignment

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.cbslassignment.SQlLite.DataBaseHelper
import com.example.cbslassignment.extensions.loadImageUrl
import com.example.cbslassignment.model.musiclistmodel
import com.google.gson.Gson
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*

internal class MoviesAdapter(moviesList: ArrayList<musiclistmodel>?, context: Context?) : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    var context: Context?
    var moviesList: ArrayList<musiclistmodel>?

    // initializer block
    init {
        this.context = context
        this.moviesList = moviesList


    }

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.textview)
        var imageview: ImageView = view.findViewById(R.id.imageview)
        var mainlayout :LinearLayout =view.findViewById(R.id.mainlayout);

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.musiclistlayout, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val movie = moviesList?.get(position)
        if(movie?.trackName!=null){
            holder.title.text = movie.trackName
        }else{
            holder.title.text = "Song Name Not found"
        }

        //(cache Image run in banner )
        //new DiskLruImageCache(context, Utils_Cache.CACHE_FILEPATH_BANNER,Utils_Cache.CACHE_FILE_SIZE, Bitmap.CompressFormat.PNG,100);
        //imgView.setImageBitmap(DiskLruImageCache.containsKey(Utils_Cache.BANNER_PREFIX+bannerVO.getBannerId()) ? DiskLruImageCache.getBitmap(Utils_Cache.BANNER_PREFIX+bannerVO.getBannerId()) :null);
        holder.imageview.loadImageUrl(movie?.artworkUrl100 ?: "")
        /*Picasso.with(context)
            .load(movie?.artworkUrl100)
            .placeholder(R.drawable.loadimage)
            .into(holder.imageview, object : Callback {
                override fun onSuccess() {
                }

                override fun onError() {

                }
            })*/

        holder.mainlayout.setOnClickListener(View.OnClickListener {

          /*  val sharedPreferences =  context?.getSharedPreferences("CBSLASession",Context.MODE_PRIVATE)
             if(sharedPreferences?.contains("historydata")!!){
                 val musiclist:ArrayList<musiclistmodel> =Gson().fromJson(sharedPreferences.getString("historydata" ,null),object : TypeToken<List<musiclistmodel?>?>() {}.type
                 ) as ArrayList<musiclistmodel>
                 var addvalue: Boolean?=false;
                 for (musiclistmodel in musiclist) {
                     if(musiclistmodel.previewUrl==movie?.previewUrl){
                         addvalue=true
                     }
                 }
                 if(!addvalue!!){
                     musiclist.add(movie!!);
                     val editor = sharedPreferences.edit()
                     editor?.putString("historydata",Gson().toJson(musiclist))
                     editor?.apply()
                 }

             }else{
                 val list : ArrayList <musiclistmodel> =ArrayList();
                 list.add(movie!!);

                 val editor = sharedPreferences.edit()
                 editor?.putString("historydata",Gson().toJson(list))
                 editor?.apply()
             }
*/

            val dataBaseHelper = DataBaseHelper(context!!)
            dataBaseHelper.createDataBase()
            dataBaseHelper.openDataBase()
            val sql = "Select * from  history where songid ="+ movie?.trackId
            val cursor: Cursor? = dataBaseHelper.myDataBase?.rawQuery(sql, null)
            if(cursor!=null && cursor.getCount()<=0){
                val contentValues = ContentValues()
                contentValues.put("songid",movie?.trackId)
                contentValues.put("details",Gson().toJson(movie))

                dataBaseHelper.myDataBase?.beginTransaction()
                dataBaseHelper.myDataBase?.insert("history", null, contentValues)
                dataBaseHelper.myDataBase?.setTransactionSuccessful()
                dataBaseHelper.myDataBase?.endTransaction()
                dataBaseHelper.close()
            }else{
                //String sql= "delete  from  'notification' ";
                //DataBaseHelper.myDataBase.execSQL(sql);
              //  dataBaseHelper.myDataBase?.delete("history", "songid="+movie?.trackId, null)

                val sql = "delete from  history where songid ="+ movie?.trackId
                val cursor: Boolean = dataBaseHelper.myDataBase?.rawQuery(sql, null)?.moveToFirst() ?: false

                val contentValues = ContentValues()
                contentValues.put("songid",movie?.trackId)
                contentValues.put("details",Gson().toJson(movie))

                dataBaseHelper.myDataBase?.beginTransaction()
                dataBaseHelper.myDataBase?.insert("history", null, contentValues)
                dataBaseHelper.myDataBase?.setTransactionSuccessful()
                dataBaseHelper.myDataBase?.endTransaction()
                dataBaseHelper.close()

            }


            val mainIntent = Intent(context, videoinfo::class.java)
            mainIntent.putExtra("data",Gson().toJson(movie))
            (context as Activity).startActivity(mainIntent)


        })


    }
    override fun getItemCount(): Int {
        return moviesList!!.size
    }




}