package com.example.cbslassignment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.cbslassignment.ui.main.PlaceholderFragment
import com.example.cbslassignment.ui.main.SectionsPagerAdapter
import com.example.mvvmkotlinexample.viewmodel.MainActivityViewModel
import com.google.android.material.tabs.TabLayout
import okhttp3.internal.notify
import okhttp3.internal.notifyAll


class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        context = this@MainActivity

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled( position: Int,positionOffset: Float,positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.
            }
        })

        val PERMISSION_ALL = 1
        val PERMISSIONS = arrayOf(

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        if (!hasPermissions(this, PERMISSIONS.toString())) {
            //ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }
    }


    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}