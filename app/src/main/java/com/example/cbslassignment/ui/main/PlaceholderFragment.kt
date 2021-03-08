package com.example.cbslassignment.ui.main

import android.content.Context
import android.database.Cursor
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cbslassignment.MoviesAdapter
import com.example.cbslassignment.R
import com.example.cbslassignment.SQlLite.DataBaseHelper
import com.example.cbslassignment.model.musiclistmodel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() ,  SwipeRefreshLayout.OnRefreshListener{

    private lateinit var pageViewModel: PageViewModel
    private lateinit var recyclerView: RecyclerView
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var nohistory: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
      //  val textView: TextView = root.findViewById(R.id.section_label)
      /*  pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })*/

        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SwipeRefreshLayout
        mSwipeRefreshLayout =
            view.findViewById<View>(R.id.swipe_container) as SwipeRefreshLayout

        recyclerView = view.findViewById(R.id.recyclerview)
        nohistory=view.findViewById(R.id.nohistory);





        mSwipeRefreshLayout!!.setOnRefreshListener(this)
        mSwipeRefreshLayout!!.setColorSchemeResources(
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        mSwipeRefreshLayout!!.post {
            mSwipeRefreshLayout!!.isRefreshing = true
            // Fetching data from server
            // loadRecyclerViewData();
            loadRecyclerViewData()
        }
    }


    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onRefresh() {
        loadRecyclerViewData()
    }

    fun loadRecyclerViewData() {
        val musiclist: ArrayList<musiclistmodel>?= ArrayList();


        val dataBaseHelper = DataBaseHelper(context!!)
        dataBaseHelper.createDataBase()
        dataBaseHelper.openDataBase()
        val sql = "Select * from  history"
        val cursor: Cursor? = dataBaseHelper.myDataBase?.rawQuery(sql, null)
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val details = cursor.getString(cursor.getColumnIndex("details"))
                musiclist?.add(Gson().fromJson(details.toString(),musiclistmodel::class.java))
                cursor.moveToNext()
                mSwipeRefreshLayout!!.isRefreshing = true
            }
        }

        dataBaseHelper.close()


        var intSize : Int? =musiclist?.size;
        if(intSize!! >0){
            nohistory.visibility=View.GONE
            var moviesAdapter: MoviesAdapter = MoviesAdapter(musiclist, context)
            val layoutManager = LinearLayoutManager(context)
            layoutManager.setReverseLayout(true)
            layoutManager.setStackFromEnd(true)

            recyclerView.layoutManager = layoutManager
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = moviesAdapter
            moviesAdapter.notifyDataSetChanged()
        }else{
            nohistory.visibility=View.VISIBLE

        }



        mSwipeRefreshLayout!!.isRefreshing = false






        /* mSwipeRefreshLayout!!.isRefreshing = true
         val sharedPreferences =  context?.getSharedPreferences("CBSLASession", Context.MODE_PRIVATE)
         if(sharedPreferences?.contains("historydata")!!) {

             val musiclist: ArrayList<musiclistmodel> =
                 Gson().fromJson(
                     sharedPreferences.getString("historydata", null),
                     object : TypeToken<List<musiclistmodel?>?>() {}.type
                 ) as ArrayList<musiclistmodel>
             var moviesAdapter: MoviesAdapter = MoviesAdapter(musiclist, context)
             val layoutManager = LinearLayoutManager(context)
             layoutManager.setReverseLayout(true)
             layoutManager.setStackFromEnd(true)

             recyclerView.layoutManager = layoutManager
             recyclerView.itemAnimator = DefaultItemAnimator()
             recyclerView.adapter = moviesAdapter
             moviesAdapter.notifyDataSetChanged()
             mSwipeRefreshLayout!!.isRefreshing = false

         }else{
             mSwipeRefreshLayout!!.isRefreshing = false
         }*/
    }

}