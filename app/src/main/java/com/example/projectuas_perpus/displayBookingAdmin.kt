package com.example.projectuas_perpus

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME4

class displayBookingAdmin : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase
    private lateinit var recycler: RecyclerView
    private var models = ArrayList<ModelBookingAdmin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitydisplaylistbooking_admin)
                dbHelper = DBHelper(this)

                recycler = findViewById(R.id.rvlistBuku)
                recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        displayData()
    }

    private fun displayData(){
        val extra = getIntent()
        val username = extra.getStringExtra("username").toString()
        sqLiteDatabase = dbHelper.readableDatabase
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLENAME4 +"", null)
        var model: ModelBookingAdmin

        while (cursor.moveToNext()){
            model = ModelBookingAdmin()
            model.id = cursor.getInt(0)
            model.id_buku = cursor.getInt(1)
            model.username = cursor.getString(2)
            model.status = cursor.getString(3)
            model.tgl_pinjam = cursor.getString(4)
            model.tgl_kembali = cursor.getString(5)
            models.add(model)
        }
        cursor.close()
        recycler.adapter =MyAdapterBookingAdmin(this@displayBookingAdmin,models,sqLiteDatabase)
    }
}