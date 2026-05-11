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

class MyAdapterBookingMember(private var context: Context, private var modelArrayList: ArrayList<ModelBookingMember>, private var sqLiteDatabase: SQLiteDatabase) : RecyclerView.Adapter<MyAdapterBookingMember.ViewHolder>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.listbooking_member, null)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model   = modelArrayList[position]
        var judul:String = ""
        var penulis:String = ""
        var jenis:String = ""
        var genre:String = ""
        var image: ByteArray = byteArrayOf()
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLENAME3 +" WHERE id_buku = "+model.id_buku+"", null)
        while (cursor.moveToNext()){
            image = cursor.getBlob(1)
            val bitmap  = BitmapFactory.decodeByteArray(image, 0, image!!.size)
            holder.imagebuku.setImageBitmap(bitmap)
            judul = cursor.getString(2)
            penulis = cursor.getString(3)
            jenis = cursor.getString(4)
            genre = cursor.getString(5)
        }
        cursor.close()
        val tglPinjam = LocalDate.parse(model.tgl_pinjam, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val tglKembali = LocalDate.parse(model.tgl_kembali, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val today = LocalDate.now()
        holder.txtjudul.text = judul
        holder.txtpinjam.text = model.tgl_pinjam
        holder.txtkembali.text = model.tgl_kembali
        holder.flowmenu.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.flowmenu)
            popupMenu.inflate(R.menu.flow_menubooking_member)
            popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.info_menu -> {
                        val bundle = Bundle()
                        bundle.putString("username", model.username)
                        bundle.putInt("id", model.id)
                        bundle.putByteArray("imageBuku", image)
                        bundle.putString("judul", judul)
                        bundle.putString("penulis", penulis)
                        bundle.putString("jenis", jenis)
                        bundle.putString("genre", genre)
                        val intent = Intent(context, infoBukuBookingMember::class.java)
                        intent.putExtra("databuku", bundle)
                        context.startActivity(intent)
                    }

                    R.id.kembali_menu -> {
                        val cv = ContentValues()
                        if (tglKembali < today){
                            cv.put("status", "telat dikembalikan")
                        }else{
                            cv.put("status", "dikembalikan")
                        }
                        val dbHelper = DBHelper(context)
                        sqLiteDatabase = dbHelper.writableDatabase
                        sqLiteDatabase.update(TABLENAME4, cv, "id_kartu="+model.id+"", null)
                        val intent = Intent(context, memberPage::class.java)
                        intent.putExtra("username", model.username)
                        context.startActivity(intent)
                    }
                }
                false
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagebuku       : ImageView
        var txtjudul        : TextView
        var txtpinjam      : TextView
        var txtkembali        : TextView
        var flowmenu : ImageButton
        init {
            imagebuku = itemView.findViewById(R.id.fotobukuBook)
            txtjudul     = itemView.findViewById(R.id.judulbookMember)
            txtpinjam = itemView.findViewById(R.id.tglpinjamBook)
            txtkembali = itemView.findViewById(R.id.tglkembaliBook)
            flowmenu = itemView.findViewById(R.id.flowmenuBookingMember)
        }
    }
}