package com.example.projectuas_perpus

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME4
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class bookings : AppCompatActivity() {
    private lateinit var dbHelper       : DBHelper
    private lateinit var sqLiteDatabase : SQLiteDatabase

    private lateinit var gambarBuku: ImageView
    private lateinit var bitmap         : Bitmap

    private lateinit var tvdatepicker  : TextView
    private lateinit var judulBuku: TextView
    private lateinit var username: TextView
    private lateinit var tglKembali: EditText

    private lateinit var btntglkembali : Button
    private lateinit var btnPinjam: Button
    private lateinit var btnKembali: Button

    private var users :String = ""
    private val calendar = Calendar.getInstance()
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formbooking)
        dbHelper            = DBHelper(this)
        sqLiteDatabase      = dbHelper.writableDatabase

        tvdatepicker      = findViewById(R.id.tglbooking2)
        judulBuku = findViewById(R.id.judul_bukubooking2)
        username = findViewById(R.id.username_booking2)
        tglKembali = findViewById(R.id.editTextDate)

        btntglkembali   = findViewById(R.id.btntglkembali)
        btnPinjam = findViewById(R.id.btnpinjam)
        btnKembali = findViewById(R.id.btnkembaliBooking)

        gambarBuku = findViewById(R.id.foto_bukubooking)
        val bundle = intent.getBundleExtra("databuku")

        if (bundle!=null){
            id = bundle.getInt("id")
            username.setText(bundle.getString("username"))
            judulBuku.setText(bundle.getString("judul"))
            val bytes   = bundle.getByteArray("imageBuku")
            bitmap      = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
            gambarBuku.setImageBitmap(bitmap)
            users = bundle.getString("username").toString()
        }

        val formats = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val curr = LocalDate.now().format(formats)
        tvdatepicker.setText(curr)

        btntglkembali.setOnClickListener {
            showDatePicker()
        }

        btnKembali.setOnClickListener {
            val intent = Intent(this@bookings, displayBukuMember::class.java)
            intent.putExtra("username", users)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        btnPinjam.setOnClickListener {
            if (tglKembali.text.toString().isEmpty()){
                Toast.makeText(this@bookings, "Pilih tanggal kembali terlebih dahulu", Toast.LENGTH_SHORT).show()
            }else{

                val tglPinjam = LocalDate.parse(tvdatepicker.text.toString(), DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                val tglKembali2 = LocalDate.parse(tglKembali.text.toString(), DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                if (tglKembali2<tglPinjam){
                    Toast.makeText(this@bookings, "Tanggal Pinjam tidak boleh melewati tanggal kembali!!", Toast.LENGTH_SHORT).show()
                }else{
                    pinjam()
                }

            }


        }
    }

    private fun showDatePicker() {
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                tglKembali.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    private fun pinjam(){
        val cv = ContentValues()
        cv.put("username", username.text.toString())
        cv.put("id_buku", id)
        cv.put("status", "di pinjam")
        cv.put("tgl_pinjam", tvdatepicker.text.toString())
        cv.put("tgl_kembali", tglKembali.text.toString())
        val cursor1 = sqLiteDatabase.rawQuery("SELECT DISTINCT(status) FROM $TABLENAME4 where username = '$users' AND id_buku = $id AND status = 'telat dikembalikan'", null)
        if (cursor1.moveToNext()){
            Toast.makeText(this@bookings, "Anda tidak bisa meminjam buku ini lagi karena pernah telat mengembalikkan", Toast.LENGTH_SHORT).show()
        }else{
            val cursor2 = sqLiteDatabase.rawQuery("SELECT DISTINCT(status) FROM $TABLENAME4 where username = '$users' AND id_buku = $id AND status = 'di pinjam'", null)
            if (cursor2.moveToNext()){
                Toast.makeText(this@bookings, "Anda tidak bisa meminjam buku ini, karena belum dikembalikkan", Toast.LENGTH_SHORT).show()
            }else{
                sqLiteDatabase.insert(TABLENAME4, null, cv)
                val intent = Intent(this@bookings, displayBukuMember::class.java)
                Toast.makeText(this@bookings, "Berhasil meminjam buku", Toast.LENGTH_SHORT).show()
                intent.putExtra("username", users)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

    }
}