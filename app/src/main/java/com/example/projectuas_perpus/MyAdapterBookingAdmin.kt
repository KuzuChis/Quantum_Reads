package com.example.projectuas_perpus

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME3
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME4
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.sql.Blob
import java.sql.Date
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MyAdapterBookingAdmin(private var context: Context, private var modelArrayList: ArrayList<ModelBookingAdmin>, private var sqLiteDatabase: SQLiteDatabase) : RecyclerView.Adapter<MyAdapterBookingAdmin.ViewHolder>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.listbooking_admin, null)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model   = modelArrayList[position]
        holder.txtid.text = model.id_buku.toString()
        holder.txtpinjam.text = model.tgl_pinjam
        holder.txtkembali.text = model.tgl_kembali
        holder.status.text = model.status
        holder.username.text = model.username
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username       : TextView
        var txtid        : TextView
        var txtpinjam      : TextView
        var txtkembali        : TextView
        var status : TextView
        init {
            username = itemView.findViewById(R.id.UsernameMember)
            txtid     = itemView.findViewById(R.id.judulbooking)
            txtpinjam = itemView.findViewById(R.id.tglPinjam)
            txtkembali = itemView.findViewById(R.id.tglKembali)
            status = itemView.findViewById(R.id.status)
        }
    }
}