package com.example.testappinventaris.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.testappinventaris.MainActivity
import com.example.testappinventaris.R
import com.example.testappinventaris.UpdateActivity
import com.example.testappinventaris.data.AppDatabase
import com.example.testappinventaris.data.entity.AlatRuangan

class MainAdapter(private var dataList: List<AlatRuangan>, private val context: Context) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val database: AppDatabase = AppDatabase.getInstance(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaBarangView: TextView = itemView.findViewById(R.id.namaBarangView)
        val namaRuanganView: TextView = itemView.findViewById(R.id.namaRuanganView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inventaris_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.namaBarangView.text = item.alat.namaAlat
        holder.namaRuanganView.text = item.ruangan.namaRuangan

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("alatId",item.alat.alatId)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Konfirmasi Hapus")
                setMessage("Apakah Anda yakin ingin menghapus data ini?")
                setPositiveButton("Ya") { _, _ ->
                    Thread {
                        try {
                            // Pastikan item masih ada
                            if (item.alat != null) {
                                database.alatDao().delete(item.alat)
                            }
                            if (item.ruangan != null) {
                                database.ruanganDao().delete(item.ruangan)
                            }

                            // Dapatkan data terbaru
                            val newDataList = database.alatRuanganDao().getAlatDanRuangan()

                            // Perbarui UI di thread utama
                            (context as MainActivity).runOnUiThread {
                                refreshData(newDataList)
                                Toast.makeText(holder.itemView.context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            // Tampilkan pesan kesalahan di UI thread
                            (context as MainActivity).runOnUiThread {
                                Toast.makeText(holder.itemView.context, "Gagal menghapus data: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }.start()
                }
                setNegativeButton("Tidak", null)
            }.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newDataList: List<AlatRuangan>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}
