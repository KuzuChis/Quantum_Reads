package com.example.projectuas_perpus

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME3

class displayBukuAdmin : AppCompatActivity() {
    private lateinit var insertImage: Button
    private lateinit var insertBuku: TextView

    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase
    private lateinit var recycler: RecyclerView
    private var models = ArrayList<ModelBuku>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitydisplaybuku_admin)
        dbHelper = DBHelper(this)
        insertImage = findViewById(R.id.btn_add)
        insertBuku = findViewById(R.id.txt_add)

        insertImage.setOnClickListener {
            val intent = Intent(this@displayBukuAdmin, InsertBuku::class.java)
            startActivity(intent)
        }

        insertBuku.setOnClickListener{
            val intent = Intent(this@displayBukuAdmin, InsertBuku::class.java)
            startActivity(intent)
        }

        recycler = findViewById(R.id.rvBookAdmin)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        displayData()
    }

    private fun displayData(){
        sqLiteDatabase = dbHelper.readableDatabase
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM $TABLENAME3", null)
        var model: ModelBuku

        while(cursor.moveToNext()){
            model = ModelBuku()
            model.id = cursor.getInt(0)
            model.imageBuku = cursor.getBlob(1)
            model.judul = cursor.getString(2)
            model.penulis = cursor.getString(3)
            model.jenis = cursor.getString(4)
            model.genre = cursor.getString(5)
            models.add(model)
        }
        cursor.close()
        recycler.adapter =MyAdapterBookAdmin(this@displayBukuAdmin,models,sqLiteDatabase)
    }


}