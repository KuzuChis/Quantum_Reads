package com.example.projectuas_perpus

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME1

class displayMemberAdmin : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase
    private lateinit var recycler: RecyclerView
    private var models = ArrayList<ModelMember>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitydisplaymember_admin)
        dbHelper = DBHelper(this)

        recycler = findViewById(R.id.rvMemberAdmin)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        displayData()
    }

    private fun displayData(){
        sqLiteDatabase = dbHelper.readableDatabase
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM $TABLENAME1", null)
        var model: ModelMember
        while (cursor.moveToNext()){
            model = ModelMember()
            model.username = cursor.getString(0)
            model.name = cursor.getString(1)
            model.gmail = cursor.getString(2)
            model.telepon = cursor.getString(3)
            model.alamat = cursor.getString(4)
            model.password = cursor.getString(5)
            models.add(model)
        }
        cursor.close()
        recycler.adapter =MyAdapterMemberAdmin(this@displayMemberAdmin,models,sqLiteDatabase)
    }
}