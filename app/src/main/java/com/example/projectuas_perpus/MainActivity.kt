package com.example.projectuas_perpus

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME1
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase

    private  lateinit var signUpBtn : Button
    private lateinit var loginBtn : Button

    private lateinit var usernameLog : EditText
    private lateinit var pass : EditText
    private lateinit var forgetPw : TextView
    private lateinit var toLoginAdmin : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper            = DBHelper(this)
        sqLiteDatabase      = dbHelper.writableDatabase

        signUpBtn = findViewById(R.id.btnSignUp)
        loginBtn = findViewById(R.id.btnLogin)

        usernameLog = findViewById(R.id.editusername)
        pass = findViewById(R.id.editPassword)
        forgetPw = findViewById(R.id.forget)
        toLoginAdmin = findViewById(R.id.adminLogin)

        signUpBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, Register::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            login()
        }

        forgetPw.setOnClickListener{
            val intent = Intent(this@MainActivity, forgetPass::class.java)
            startActivity(intent)
        }

        toLoginAdmin.setOnClickListener{
            val intent = Intent(this@MainActivity, loginAdmin::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("Range")
    private fun login(){
        val cekUser = usernameLog.text.toString()
        val cekPass = pass.text.toString()

        if(cekUser.isEmpty()||cekPass.isEmpty()){
            Toast.makeText(this@MainActivity, "Field Username atau Password belum di input", Toast.LENGTH_SHORT).show()
        }else{
            val query = "SELECT * FROM $TABLENAME1 WHERE username = ? AND password = ?"
            val selectionArgs = arrayOf(cekUser, cekPass)
            val cursor: Cursor = sqLiteDatabase.rawQuery(query, selectionArgs)

            if (cursor.moveToFirst()){
                val username = cursor.getString(0)
                val name =  cursor.getString(1)
                val intent = Intent(this@MainActivity, memberPage::class.java)
                intent.putExtra("username", username)
                intent.putExtra("nama", name)
                Toast.makeText(this@MainActivity, "berhasil login", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                Toast.makeText(this@MainActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
        }


    }
}