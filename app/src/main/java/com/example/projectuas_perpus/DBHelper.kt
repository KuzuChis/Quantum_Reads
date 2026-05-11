package com.example.projectuas_perpus

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DBNAME, null, VER) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val query =
            "create table " + TABLENAME1 + "(username text primary key, name text, gmail text, telepon text, alamat text, password text)"
        sqLiteDatabase.execSQL(query)

        val query2 = "create table " + TABLENAME2 + "(username text primary key, password text)"
        sqLiteDatabase.execSQL(query2)

        val query3 =
            "create table " + TABLENAME3 + "(id_buku integer primary key, image_buku blob, judul text, penulis text, jenis text, genre text)"
        sqLiteDatabase.execSQL(query3)

        val query4 ="create table " + TABLENAME4 + "(id_kartu integer primary key autoincrement, id_buku integer, username text, status text,  tgl_pinjam date, tgl_kembali date,  " +
                "FOREIGN KEY(id_buku) REFERENCES $TABLENAME3(id_buku), " +
                "FOREIGN KEY(username) REFERENCES $TABLENAME1(username))"
        sqLiteDatabase.execSQL(query4)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        val query = "drop table if exists " + TABLENAME1 + ""
        sqLiteDatabase.execSQL(query)

        val query2 = "drop table if exists "+ TABLENAME2 +""
        sqLiteDatabase.execSQL(query2)

        val query3 = "drop table if exists "+ TABLENAME3 +""
        sqLiteDatabase.execSQL(query3)

        val query4 = "drop table if exists "+ TABLENAME4+""
        sqLiteDatabase.execSQL(query4)
    }

    companion object {
        const val DBNAME = "quantumReads.db"
        const val TABLENAME1 = "member"
        const val TABLENAME2 = "admin"
        const val TABLENAME3 = "buku"
        const val TABLENAME4 = "kartu_pinjam"
        const val VER = 1
    }
}