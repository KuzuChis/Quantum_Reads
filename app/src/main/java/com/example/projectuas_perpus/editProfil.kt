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

class editProfil : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var sqLiteDatabase: SQLiteDatabase

    private lateinit var editNama : EditText
    private lateinit var editEmail : EditText
    private lateinit var editTelp : EditText
    private lateinit var editAlamat : EditText

    private lateinit var cancel : Button
    private lateinit var submit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)
        val intent = getIntent()
//        Toast.makeText(this@editProfil, "berhasil masuk ke editprofil", Toast.LENGTH_SHORT).show()
        dbHelper            = DBHelper(this)
        sqLiteDatabase      = dbHelper.writableDatabase

        editNama = findViewById(R.id.edit_namalengkap_editProfil)
        editEmail = findViewById(R.id.editemail_editProfil)
        editTelp = findViewById(R.id.edittelepon_editProfil)
        editAlamat = findViewById(R.id.editalamat_editProfil)

        cancel = findViewById(R.id.app_cancel)
        submit = findViewById(R.id.app_submit)
        val username : String = intent.getStringExtra("username").toString()
        cancel.setOnClickListener {
            val intent = Intent(this@editProfil, memberPage::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
//
        val query = "SELECT * FROM $TABLENAME1 WHERE username = ?"
        val selectionArgs = arrayOf(username)
        val cursor: Cursor = sqLiteDatabase.rawQuery(query, selectionArgs)
        Toast.makeText(this@editProfil, "berhasil isi field", Toast.LENGTH_SHORT).show()
        if (cursor.moveToFirst()){
            editNama.setText(cursor.getString(1))
            editEmail.setText(cursor.getString(2))
            editTelp.setText(cursor.getString(3))
            editAlamat.setText(cursor.getString(4))
        }

//
        submit.setOnClickListener {
            changeProfil(username)
        }
    }

    private fun changeProfil(username: String){
        val nameNew = editNama.text.toString()
        val emailNew = editEmail.text.toString()
        val telpNew = editTelp.text.toString()
        val alamatNew = editAlamat.text.toString()

        if (nameNew.isEmpty()||emailNew.isEmpty()||telpNew.isEmpty()||alamatNew.isEmpty()){
            Toast.makeText(this@editProfil, "Harap isi semua Field!!", Toast.LENGTH_SHORT).show()
        }else{
            val cv = ContentValues()

            cv.put("name", nameNew)
            cv.put("gmail", emailNew)
            cv.put("telepon", telpNew)
            cv.put("alamat", alamatNew)

            sqLiteDatabase.update(TABLENAME1, cv, "username = ?", arrayOf(username))
            Toast.makeText(this@editProfil, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@editProfil, memberPage::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
    }
}