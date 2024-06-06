package com.example.testappinventaris

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testappinventaris.data.AppDatabase
import com.example.testappinventaris.data.entity.Alat
import com.example.testappinventaris.data.entity.Ruangan
import com.example.testappinventaris.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(applicationContext)

        binding.backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.saveButton.setOnClickListener {
            if (binding.uploadRuangan.text.isNotEmpty() && binding.uploadNamaBarang.text.isNotEmpty() &&
                binding.uploadSpesifikasiBarang.text.isNotEmpty() && binding.uploadJumlahBarang.text.isNotEmpty() &&
                binding.uploadKegunaanBarang.text.isNotEmpty()) {

                Thread {
                    val alatId = database.alatDao().insert(
                        Alat(
                            null,
                            binding.uploadNamaBarang.text.toString(),
                            binding.uploadSpesifikasiBarang.text.toString(),
                            binding.uploadJumlahBarang.text.toString(),
                            binding.uploadKegunaanBarang.text.toString()
                        )
                    )

                    database.ruanganDao().insert(
                        Ruangan(
                            null,
                            binding.uploadRuangan.text.toString(),
                            alatId.toInt()
                        )
                    )

                    runOnUiThread {
                        Toast.makeText(this@UploadActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.start()
            } else {
                Toast.makeText(applicationContext, "Silakan isi semua data dengan benar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}