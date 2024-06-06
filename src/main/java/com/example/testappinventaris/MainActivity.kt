package com.example.testappinventaris

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testappinventaris.data.AppDatabase
import com.example.testappinventaris.data.adapter.MainAdapter
import com.example.testappinventaris.data.entity.AlatRuangan
import com.example.testappinventaris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var list = mutableListOf<AlatRuangan>()
    private lateinit var adapter: MainAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(applicationContext)
        adapter = MainAdapter(list, this)

        binding.inventarisRecyclerView.adapter = adapter
        binding.inventarisRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        binding.inventarisRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, RecyclerView.VERTICAL))

        binding.uploadButton.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }

        getData()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        // Akses database di thread terpisah
        Thread {
            val data = database.alatRuanganDao().getAlatDanRuangan()
            runOnUiThread {
                list.clear()
                list.addAll(data)
                adapter.notifyDataSetChanged()
            }
        }.start()
    }
}

