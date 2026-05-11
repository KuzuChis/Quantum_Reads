package com.example.projectuas_perpus

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class infoBukuMember : AppCompatActivity() {
    private lateinit var btnBack: Button

    private var username: String = ""

    private lateinit var judulBuku: TextView
    private lateinit var penerbitBuku: TextView
    private lateinit var jenisBuku: TextView
    private lateinit var genreBuku: TextView

    private lateinit var gambarBuku: ImageView
    private lateinit var bitmap         : Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_buku)

        btnBack = findViewById(R.id.btnkembali)
        judulBuku = findViewById(R.id.judul_bukuinfo2)
        penerbitBuku = findViewById(R.id.penerbit_bukuinfo2)
        jenisBuku = findViewById(R.id.jenis_bukuinfo2)
        genreBuku = findViewById(R.id.genre_bukuinfo2)
        gambarBuku = findViewById(R.id.foto_bukuinfo)

        val bundle = intent.getBundleExtra("databuku")

        if (bundle!=null){
            username = bundle.getString("username").toString()
            judulBuku.setText(bundle.getString("judul"))
            val bytes   = bundle.getByteArray("imageBuku")
            bitmap      = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
            gambarBuku.setImageBitmap(bitmap)
            penerbitBuku.setText(bundle.getString("penulis"))
            jenisBuku.setText(bundle.getString("jenis"))
            genreBuku.setText(bundle.getString("genre"))
        }
        btnBack.setOnClickListener {
            val intent = Intent(this@infoBukuMember, displayBukuMember::class.java)
            intent.putExtra("username", username)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }
}