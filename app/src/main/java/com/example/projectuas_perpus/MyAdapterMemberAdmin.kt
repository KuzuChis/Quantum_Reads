package com.example.projectuas_perpus

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuas_perpus.DBHelper.Companion.TABLENAME1
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MyAdapterMemberAdmin(private var context: Context, private var modelArrayList: ArrayList<ModelMember>, private var sqLiteDatabase: SQLiteDatabase) : RecyclerView.Adapter<MyAdapterMemberAdmin.ViewHolder>() {
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.memberadmin, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model   = modelArrayList[position]
        holder.txtusername.text = model.username
        holder.txtname.text = model.name
        holder.txtgmail.text = model.gmail
        holder.txttelepon.text = model.telepon
        holder.txtalamat.text = model.alamat
        holder.delete.setOnClickListener {
            MaterialAlertDialogBuilder(context).setTitle("Delete").setMessage("Yakin hapus?")
                .setPositiveButton("Delete") { _, _ ->
                    val dbHelper = DBHelper(context)
                    sqLiteDatabase = dbHelper.readableDatabase
                    sqLiteDatabase.delete(TABLENAME1, "username='"+model.username+"'", null)
                    Toast.makeText(context, "Member Berhasil Dihapus!!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, adminPage::class.java)
                    context.startActivity(intent)
                }
                .setNegativeButton("Cancel"){_,_->}.show()
        }
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtusername       : TextView
        var txtname        : TextView
        var txtgmail      : TextView
        var txttelepon       : TextView
        var txtalamat        : TextView
        var delete       : Button

        init {
            txtusername = itemView.findViewById(R.id.uname)
            txtname    = itemView.findViewById(R.id.nama)
            txtgmail = itemView.findViewById(R.id.gmail)
            txttelepon = itemView.findViewById(R.id.telp)
            txtalamat = itemView.findViewById(R.id.alamat)
            delete    = itemView.findViewById(R.id.btn_delete)
        }
    }
}