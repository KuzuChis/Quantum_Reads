package com.example.projectuas_perpus

import android.annotation.SuppressLint
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MyAdapterBookAdmin(private var context: Context, private var modelArrayList: ArrayList<ModelBuku>, private var sqLiteDatabase: SQLiteDatabase) : RecyclerView.Adapter<MyAdapterBookAdmin.ViewHolder>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.bukuadmin, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model   = modelArrayList[position]
        val image   = model.imageBuku
        val bitmap  = BitmapFactory.decodeByteArray(image, 0, image!!.size)
        holder.imagebuku.setImageBitmap(bitmap)
        holder.txtjudul.text = model.judul
        holder.txtpenulis.text = model.penulis
        holder.txtjenis.text = model.jenis
        holder.txtgenre.text = model.genre
        holder.flowmenu.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.flowmenu)
            popupMenu.inflate(R.menu.flow_menubuku_admin)
            popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.edit_menu -> {
                        val bundle = Bundle()
                        bundle.putInt("id", model.id)
                        bundle.putByteArray("imageBuku", model.imageBuku)
                        bundle.putString("judul", model.judul)
                        bundle.putString("penulis", model.penulis)
                        bundle.putString("jenis", model.jenis)
                        bundle.putString("genre", model.genre)
                        val intent = Intent(context, InsertBuku::class.java)
                        intent.putExtra("databuku", bundle)
                        context.startActivity(intent)
                    }

                    R.id.delete_menu -> {
                        MaterialAlertDialogBuilder(context).setTitle("Delete").setMessage("Yakin hapus?")
                            .setPositiveButton("Delete") { _, _ ->
                                val dbHelper = DBHelper(context)
                                sqLiteDatabase = dbHelper.readableDatabase
                                sqLiteDatabase.delete(TABLENAME3, "id_buku="+model.id, null)
                                Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, adminPage::class.java)
                                context.startActivity(intent)
                            }
                            .setNegativeButton("Cancel"){_,_->}.show()
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
        var txtpenulis      : TextView
        var txtjenis        : TextView
        var txtgenre         : TextView
        var flowmenu        : ImageButton

        init {
            imagebuku = itemView.findViewById(R.id.fotobuku)
            txtjudul     = itemView.findViewById(R.id.judulBukuAdmin)
            txtpenulis = itemView.findViewById(R.id.penulisBukuAdmin)
            txtjenis = itemView.findViewById(R.id.jenisBukuAdmin)
            txtgenre = itemView.findViewById(R.id.genreBukuAdmin)
            flowmenu    = itemView.findViewById(R.id.flowmenuBukuAdmin)
        }
    }
}