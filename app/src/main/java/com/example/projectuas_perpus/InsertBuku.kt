package com.example.projectuas_perpus

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME3
import java.io.ByteArrayOutputStream
import java.io.IOException

class InsertBuku : AppCompatActivity() {
    private var id        = 0
    private var resId     = 0
    private lateinit var dbHelper       : DBHelper
    private lateinit var sqLiteDatabase : SQLiteDatabase

    private lateinit var imageBuku: ImageView
    private lateinit var judul: EditText
    private lateinit var penulis: EditText
    private lateinit var jenis: EditText
    private lateinit var genre: EditText

    private lateinit var submit         : Button
    private lateinit var display        : Button
    private lateinit var edit           : Button
    private lateinit var bitmap         : Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertbukuadmin)
        dbHelper            = DBHelper(this)
        sqLiteDatabase      = dbHelper.writableDatabase

        imageBuku = findViewById(R.id.insertbuku)
        judul = findViewById(R.id.judulbuku)
        penulis = findViewById(R.id.penerbitbuku)
        jenis = findViewById(R.id.jenisbuku)
        genre = findViewById(R.id.genrebuku)

        edit = findViewById(R.id.btn_edit)
        submit = findViewById(R.id.btn_submit)
        display = findViewById(R.id.btn_display)

        imagePick()
        submit.setOnClickListener {
            insertData()
        }

        display.setOnClickListener {
            val intent = Intent(this@InsertBuku, displayBukuAdmin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        editBuku()
    }

    private fun imagePick(){
        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data!!
                val uri = data.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)//setelah mengambil gmbr ini akan mengubah tipenya menjadi bitmap
                    imageBuku.setImageBitmap(bitmap)//setelah mengambil gmbr dan di konvert ke bitmap, ditampilkan dengan ini
                    resId = 1
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        imageBuku.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val intent = Intent(Intent.ACTION_GET_CONTENT)//berpindah ke aplikasi lain
            intent.type = "image/*"
            activityResultLauncher.launch(intent)
        }
    }

    private fun insertData(){
        if (resId == 1){
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
            val ImageViewToByte = byteArrayOutputStream.toByteArray()//gambar berubah dr bitmap menjadi byte
            val cv = ContentValues()
            cv.put("image_buku", ImageViewToByte)
            cv.put("judul",   judul.text.toString())
            cv.put("penulis", penulis.text.toString())
            cv.put("jenis", jenis.text.toString())
            cv.put("genre", genre.text.toString())
            sqLiteDatabase = dbHelper.writableDatabase
            sqLiteDatabase.insert(TABLENAME3, null, cv)
            Toast.makeText(this@InsertBuku, "Inserted Successfully", Toast.LENGTH_SHORT).show()
            clear()
        }
        else{
            Toast.makeText(this,"Masukkan gambar buku terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clear(){
        resId = 0
        imageBuku.setImageResource(R.drawable.upload)
        judul.setText("")
        penulis.setText("")
        jenis.setText("")
        genre.setText("")
    }

    private fun editBuku(){
        val bundle = intent.getBundleExtra("databuku")
        if (bundle != null){
            id = bundle.getInt("id")
            val bytes   = bundle.getByteArray("imageBuku")
            bitmap      = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
            imageBuku.setImageBitmap(bitmap)
            judul.setText(bundle.getString("judul"))
            penulis.setText(bundle.getString("penulis"))
            jenis.setText(bundle.getString("jenis"))
            genre.setText(bundle.getString("genre"))

            submit.visibility   = View.GONE
            edit.visibility     = View.VISIBLE

            edit.setOnClickListener {
                if (resId==1){
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
                    val ImageViewToByte = byteArrayOutputStream.toByteArray()//gambar berubah dr bitmap menjadi byte
                    val cv = ContentValues()
                    cv.put("image_buku", ImageViewToByte)
                    cv.put("judul",   judul.text.toString())
                    cv.put("penulis", penulis.text.toString())
                    cv.put("jenis", jenis.text.toString())
                    cv.put("genre", genre.text.toString())
                    sqLiteDatabase = dbHelper.writableDatabase
                    sqLiteDatabase.update(TABLENAME3, cv, "id_buku=$id", null)
                    Toast.makeText(this@InsertBuku, "Update Successfully", Toast.LENGTH_SHORT).show()
                    clear()
                }else{
                    val cv = ContentValues()
                    cv.put("judul",   judul.text.toString())
                    cv.put("penulis", penulis.text.toString())
                    cv.put("jenis", jenis.text.toString())
                    cv.put("genre", genre.text.toString())
                    sqLiteDatabase = dbHelper.writableDatabase
                    sqLiteDatabase.update(TABLENAME3, cv, "id_buku=$id", null)
                    Toast.makeText(this@InsertBuku, "Update Successfully", Toast.LENGTH_SHORT).show()
                    clear()
                }
            }
        }
    }
}