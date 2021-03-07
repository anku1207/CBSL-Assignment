package com.example.cbslassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cbslassignment.extensions.showToast
import com.example.mvvmkotlinexample.model.ServicesSetterGetter
import com.example.mvvmkotlinexample.viewmodel.MainActivityViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [musicListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class musicListFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var recyclerView: RecyclerView
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment musicListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            musicListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerview)

        mSwipeRefreshLayout =
            view.findViewById<View>(R.id.swipe_container) as SwipeRefreshLayout



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


    override fun onRefresh() {
        loadRecyclerViewData()
    }
    private fun loadRecyclerViewData() {
        mSwipeRefreshLayout?.isRefreshing = true
        mainActivityViewModel.getSongList()?.let {
            it.observe(this, Observer { setterGetter ->
                setterGetter?.run {

                }
            })
        }
        requireContext()?.showToast("",true)
        mainActivityViewModel.getSongList()?.observe(this, Observer { serviceSetterGetter ->
            val msg = serviceSetterGetter.results


            var moviesAdapter: MoviesAdapter = MoviesAdapter(msg,context)
            val linearLayoutManager = LinearLayoutManager(context)
            recyclerView?.apply {
                layoutManager = linearLayoutManager
                itemAnimator = DefaultItemAnimator()
                adapter = moviesAdapter
            }
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = moviesAdapter
            moviesAdapter.notifyDataSetChanged()

            mSwipeRefreshLayout!!.isRefreshing = false

        })

        tempFunc ("sss"){

        }

        methodOverloading(int = 12)
    }

    fun tempFunc(string: String, callBack : (ServicesSetterGetter?) -> Unit){
        callBack.invoke(null)
    }

    fun methodOverloading(string: String? = "temp", boolean: Boolean = true, int: Int){

    }

}