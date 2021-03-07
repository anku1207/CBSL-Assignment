package com.example.mvvmkotlinexample.model

import com.example.cbslassignment.model.musiclistmodel
import org.json.JSONObject
import java.util.ArrayList

data class ServicesSetterGetter (
    var resultCount  :Int?=null,
    var results: ArrayList<musiclistmodel>? = null

)