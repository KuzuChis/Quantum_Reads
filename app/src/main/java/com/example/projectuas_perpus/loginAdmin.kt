package com.example.projectuas_perpus

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME2

class loginAdmin : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var toLoginMember : TextView
    private lateinit var registS: TextView

    private lateinit var loginBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_admin)
        dbHelper            = DBHelper(this)
        sqLiteDatabase      = dbHelper.writableDatabase

        username = findViewById(R.id.editusername_admin)
        password =  findViewById(R.id.editPassword_admin)
        toLoginMember = findViewById(R.id.memberLogin)

        loginBtn = findViewById(R.id.btnLogin_admin)

        toLoginMember.setOnClickListener{
            val intent = Intent(this@loginAdmin, MainActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val cekUser = username.text.toString()
        val cekPass = password.text.toString()

        if (cekUser.isEmpty()||cekPass.isEmpty()){
            Toast.makeText(this@loginAdmin, "Field username atau password belum diisi", Toast.LENGTH_SHORT).show()
        }else{
            if ((cekUser == "anton" && cekPass == "32220132")||(cekUser == "stevanny" && cekPass == "32220131")||(cekUser == "alvin" && cekPass == "32220146")){
                val intent = Intent(this@loginAdmin, adminPage::class.java)
                Toast.makeText(this@loginAdmin, "berhasil login", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                Toast.makeText(this@loginAdmin, "username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}