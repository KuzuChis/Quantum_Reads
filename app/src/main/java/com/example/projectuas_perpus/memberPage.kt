package com.example.projectuas_perpus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class memberPage : AppCompatActivity() {
    private lateinit var logout : Button
    private lateinit var editBtn : Button
    private lateinit var liatBuku : Button
    private lateinit var liatBooking : Button

    private lateinit var textNama : TextView

    private lateinit var imgProfil: ImageView
    private lateinit var imgBuku : ImageView
    private lateinit var imgBooking : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.member)
        val intent = getIntent()

        logout = findViewById(R.id.LogoutMember)
        textNama = findViewById(R.id.textView_judulmember)
        editBtn = findViewById(R.id.btn_info)
        imgProfil = findViewById(R.id.imageView_edit)
        liatBuku = findViewById(R.id.btn_deskirpsi)
        imgBuku = findViewById(R.id.imageView_deskripsi)
        liatBooking = findViewById(R.id.btn_pinjam)
        imgBooking = findViewById(R.id.imageView_pinjam)

        logout.setOnClickListener {
            val intent = Intent(this@memberPage, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val username : String = intent.getStringExtra("username").toString()
        textNama.text = "Selamat Datang, "+username

        editBtn.setOnClickListener {
            val intent = Intent(this@memberPage, editProfil::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        imgProfil.setOnClickListener{
            val intent = Intent(this@memberPage, editProfil::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        liatBuku.setOnClickListener {
            val intent = Intent(this@memberPage, displayBukuMember::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        imgBuku.setOnClickListener {
            val intent = Intent(this@memberPage, displayBukuMember::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        liatBooking.setOnClickListener {
            val intent = Intent(this@memberPage, displayBookingMember::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        imgBooking.setOnClickListener {
            val intent = Intent(this@memberPage, displayBookingMember::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
    }
}