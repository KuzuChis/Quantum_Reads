package com.example.projectuas_perpus

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME1

class forgetPass : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase

    private lateinit var cancel:Button
    private lateinit var submit:Button
    private lateinit var userForget : EditText
    private lateinit var mailForget : EditText
    private lateinit var pass : EditText
    private lateinit var conPass : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgetpw)
        dbHelper            = DBHelper(this)
        sqLiteDatabase      = dbHelper.writableDatabase

        userForget = findViewById(R.id.editusername_forget)
        mailForget = findViewById(R.id.editemail_forget)
        pass = findViewById(R.id.editpassword_forget)
        conPass = findViewById(R.id.editconfirmpassword_forget)

        cancel = findViewById(R.id.cancelForget)
        submit = findViewById(R.id.submitForget)

        cancel.setOnClickListener {
            val intent = Intent(this@forgetPass, MainActivity::class.java)
            startActivity(intent)
        }

        submit.setOnClickListener {
            if(pass.text.toString()==conPass.text.toString()){
                if (userForget.text.toString().isEmpty()||pass.text.toString().isEmpty()||mailForget.text.toString().isEmpty()||conPass.text.toString().isEmpty()){
                    Toast.makeText(this@forgetPass, "Harap Isi semua Field!!", Toast.LENGTH_SHORT).show()
                }else{

                    updateData()
                }
            }else{
                Toast.makeText(this@forgetPass, "Password dan Confirm Password tidak sama", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateData(){
        val passCh = pass.text.toString()
        val userCh = userForget.text.toString()
        val mailCh = mailForget.text.toString()
        val query = "SELECT * FROM  $TABLENAME1 WHERE username = ? AND gmail = ?"
        val selectionArgs = arrayOf(userCh, mailCh)
        val cursor: Cursor = sqLiteDatabase.rawQuery(query, selectionArgs)

        val cv = ContentValues()

        if(cursor.moveToFirst()){
            cv.put("password", passCh)
            sqLiteDatabase.update(TABLENAME1, cv, "username = ?", arrayOf(cursor.getString(0)))
            Toast.makeText(this@forgetPass, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@forgetPass, MainActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this@forgetPass, "Akun tidak ditemukan!!", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
    }
}