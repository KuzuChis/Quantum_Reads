package com.example.projectuas_perpus

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class adminPage : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase

    private lateinit var logout : Button
    private lateinit var textNama : TextView

    private lateinit var bukuImage : ImageView
    private lateinit var memberImage : ImageView
    private lateinit var pinjamImage : ImageView

    private lateinit var lihatBuku : Button
    private lateinit var lihatMember : Button
    private lateinit var lihatPinjam : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin)
        dbHelper            = DBHelper(this)
        sqLiteDatabase      = dbHelper.writableDatabase

        bukuImage = findViewById(R.id.imageViewbuku_admin)
        lihatBuku = findViewById(R.id.btn_lihatbuku)

        memberImage = findViewById(R.id.imageViewmember_admin)
        lihatMember = findViewById(R.id.btn_lihatmember)

        pinjamImage = findViewById(R.id.imageViewtgl_admin)
        lihatPinjam = findViewById(R.id.btn_tglpinjam)

        logout = findViewById(R.id.LogoutAdmin)
        logout.setOnClickListener {
            val intent = Intent(this@adminPage, loginAdmin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        bukuImage.setOnClickListener{
            val intent = Intent(this@adminPage, displayBukuAdmin::class.java)
            startActivity(intent)
        }

        lihatBuku.setOnClickListener {
            val intent = Intent(this@adminPage, displayBukuAdmin::class.java)
            startActivity(intent)
        }

        memberImage.setOnClickListener{
            val intent = Intent(this@adminPage, displayMemberAdmin::class.java)
            startActivity(intent)
        }

        lihatMember.setOnClickListener {
            val intent = Intent(this@adminPage, displayMemberAdmin::class.java)
            startActivity(intent)
        }

        pinjamImage.setOnClickListener{
            val intent = Intent(this@adminPage, displayBookingAdmin::class.java)
            startActivity(intent)
        }

        lihatPinjam.setOnClickListener {
            val intent = Intent(this@adminPage, displayBookingAdmin::class.java)
            startActivity(intent)
        }
    }
}