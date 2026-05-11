package com.example.projectuas_perpus

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME1

class Register : AppCompatActivity(){

    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase

    private lateinit var name: EditText
    private lateinit var username: EditText
    private lateinit var gmail: EditText
    private lateinit var telp: EditText
    private lateinit var alamat: EditText
    private lateinit var pass: EditText
    private lateinit var conpass: EditText

    private lateinit var cancel: Button
    private lateinit var submit: Button
    private lateinit var reset: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        dbHelper = DBHelper(this)
        name = findViewById(R.id.edit_namalengkap_signup)
        username = findViewById(R.id.username_signup)
        gmail = findViewById(R.id.editemail_signup)
        telp = findViewById(R.id.edittelepon_signup)
        alamat = findViewById(R.id.editalamat_signup)
        pass = findViewById(R.id.editpassword_signup)
        conpass = findViewById(R.id.editconfirmpassword_signup)

        cancel = findViewById(R.id.btnCancel)
        submit = findViewById(R.id.submitBtn)
        reset = findViewById(R.id.resetBtn)

        cancel.setOnClickListener{
            val intent = Intent(this@Register, MainActivity::class.java)
            startActivity(intent)
        }

        reset.setOnClickListener {
            clear()
        }

        submit.setOnClickListener {
            if(pass.text.toString() == conpass.text.toString()){
                if (username.text.toString().isEmpty()||name.text.toString().isEmpty()||gmail.text.toString().isEmpty()||telp.text.toString().isEmpty()||alamat.text.toString().isEmpty()||pass.text.toString().isEmpty()||conpass.text.toString().isEmpty()){
                    Toast.makeText(this@Register, "Harap Isi semua field!!", Toast.LENGTH_SHORT).show()
                }else{
                    kirimData()
                }

            }else{
                Toast.makeText(this@Register, "Password dan Confirm Password tidak sama", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun kirimData(){
        val cv = ContentValues()
        cv.put("username", username.text.toString())
        val users = username.text.toString()
        cv.put("name", name.text.toString())
        cv.put("gmail", gmail.text.toString())
        cv.put("telepon", telp.text.toString())
        cv.put("alamat", alamat.text.toString())
        cv.put("password", pass.text.toString())
        sqLiteDatabase = dbHelper.writableDatabase
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM $TABLENAME1 where username='$users'", null)
        if (cursor.moveToNext()){
            Toast.makeText(this@Register, "Username sudah digunakan", Toast.LENGTH_SHORT).show()
        }else{
            sqLiteDatabase.insert(TABLENAME1, null, cv)//klo mau masukin data ke tabel bku, pake perintah ini !!!
            Toast.makeText(this@Register, "Insert Successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@Register, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clear(){
        name.setText("")
        username.setText("")
        gmail.setText("")
        telp.setText("")
        alamat.setText("")
        pass.setText("")
        conpass.setText("")
    }
}

