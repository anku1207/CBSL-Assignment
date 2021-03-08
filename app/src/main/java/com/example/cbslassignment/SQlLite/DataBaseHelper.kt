package com.example.cbslassignment.SQlLite

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.IOException

class DataBaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    //The Android's default system path of your application database.
    var DB_PATH = "/data/data/com.example.cbslassignment/databases/"

    var DB_NAME = "CBSLAData"


   public var myDataBase: SQLiteDatabase? = null

    private var myContext: Context? = null


    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "CBSLAData"

    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    fun DataBaseHelper(context: Context?) {
        myContext = context
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        var db_Read: SQLiteDatabase? = null
        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            db_Read = this.readableDatabase
            db_Read.close()
        }
    }


    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = DB_PATH + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE)
        } catch (e: SQLiteException) {
            //database does't exist yet.
        }
        checkDB?.close()
        return if (checkDB != null) true else false
    }


    @Throws(SQLException::class)
    fun openDataBase() {
        //Open the database
        val myPath = DB_PATH + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE)

        //create a notification table
        myDataBase?.execSQL("create table if not exists history (ID INTEGER PRIMARY KEY AUTOINCREMENT,songid TEXT, details TEXT)")

    }

    @Synchronized
    override fun close() {
        if (myDataBase != null) myDataBase!!.close()
        super.close()
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {}

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, i: Int, i1: Int) {}
}
