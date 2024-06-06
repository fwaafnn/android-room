package com.example.testappinventaris

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testappinventaris.data.AppDatabase
import com.example.testappinventaris.data.entity.Alat
import com.example.testappinventaris.data.entity.Ruangan
import com.example.testappinventaris.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var database: AppDatabase
    private var ruangan: Ruangan? = null
    private var alat: Alat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(applicationContext)

        val backButtonToMain = binding.backButton
        backButtonToMain.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        val alatId = intent?.getIntExtra("alatId", 0) ?: 0
        if (alatId != 0) {
            Thread {
                alat = database.alatDao().getById(alatId)
                ruangan = database.ruanganDao().getRuanganByAlatId(alatId)

                runOnUiThread {
                    alat?.let {
                        binding.editNamaBarang.setText(it.namaAlat)
                        binding.editSpesifikasiBarang.setText(it.spesifikasi)
                        binding.editJumlahBarang.setText(it.jumlah)
                        binding.editKegunaanBarang.setText(it.kegunaan)
                    }
                    ruangan?.let {
                        binding.editRuangan.setText(it.namaRuangan)
                    }
                }
            }.start()
        }

        binding.saveButton.setOnClickListener {
            if (binding.editRuangan.text.isNotEmpty() && binding.editNamaBarang.text.isNotEmpty() &&
                binding.editSpesifikasiBarang.text.isNotEmpty() && binding.editJumlahBarang.text.isNotEmpty() &&
                binding.editKegunaanBarang.text.isNotEmpty()) {

                Thread {
                    if (alatId != 0) {
                        database.alatDao().update(
                            Alat(
                                alatId,
                                binding.editNamaBarang.text.toString(),
                                binding.editSpesifikasiBarang.text.toString(),
                                binding.editJumlahBarang.text.toString(),
                                binding.editKegunaanBarang.text.toString()
                            )
                        )

                        ruangan?.let {
                            database.ruanganDao().update(
                                Ruangan(
                                    it.ruanganId,
                                    binding.editRuangan.text.toString(),
                                    alatId
                                )
                            )
                        }

                        runOnUiThread {
                            Toast.makeText(this@UpdateActivity, "Data disimpan", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }.start()
            } else {
                Toast.makeText(applicationContext, "Silahkan isi semua data dengan valid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
