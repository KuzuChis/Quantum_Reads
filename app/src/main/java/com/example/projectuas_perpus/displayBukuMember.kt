package com.example.projectuas_perpus

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class displayBukuMember : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase
    private lateinit var recycler: RecyclerView
    private var models = ArrayList<ModelBukuMember>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitydisplaylistbuku_member)
        dbHelper = DBHelper(this)

        recycler = findViewById(R.id.rvbukuMember)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        displayData()
    }
    private fun displayData(){
        sqLiteDatabase = dbHelper.readableDatabase
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM ${DBHelper.TABLENAME3}", null)
        var model: ModelBukuMember
        val intentS = getIntent()

        while(cursor.moveToNext()){
            model = ModelBukuMember()
            model.username = intentS.getStringExtra("username").toString()
            model.id = cursor.getInt(0)
            model.imageBuku = cursor.getBlob(1)
            model.judul = cursor.getString(2)
            model.penulis = cursor.getString(3)
            model.jenis = cursor.getString(4)
            model.genre = cursor.getString(5)
            models.add(model)
        }
        cursor.close()
        recycler.adapter =MyAdapterBookMember(this@displayBukuMember,models,sqLiteDatabase)
    }
}