package com.example.myfiles

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfiles.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listFiles(Environment.getExternalStorageDirectory().absolutePath)

    }

    private fun listFiles(path: String) {
        val directory: File = File(path)

        val listFile = mutableListOf<FileData>()
        if (directory.exists()) {
            val files: Array<File> = directory.listFiles()!!
            for (i in files.indices) {
                val image = if (files[i].isFile) {
                    R.drawable.file
                } else {
                    R.drawable.folder
                }
                listFile.add(FileData(image, files[i].name, files[i].absolutePath))
            }
        }
        binding.rvListFiles.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var fileAdapter = FileAdapter(listFile)
        fileAdapter.onItemClick = {
            this.listFiles(it.absolutePath)
        }
        binding.rvListFiles.adapter = fileAdapter
    }
}