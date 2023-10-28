package com.example.myfiles

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfiles.databinding.ActivityMainBinding
import java.io.File
import java.util.Stack

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fileStack: Stack<String>
    private val REQUEST_CODE = 12

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fileStack = Stack()
        listFiles(Environment.getExternalStorageDirectory().absolutePath)
        requestPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission():Boolean {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            showToast("Permission is granted")
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE);
        }
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (fileStack.size == 0) {
            super.onBackPressed()
        }
        fileStack.pop()
        listFiles(fileStack.peek())
    }

    private fun listFiles(path: String) {
        val directory = File(path)
        if (directory.isFile) {
            return
        }
        if (fileStack.size == 0 || path != fileStack.peek()) {
            fileStack.push(path)
        }
        val listFile = mutableListOf<FileData>()
        if (directory.exists()) {
            try {
                val files: Array<File> = directory.listFiles()!!
                for (i in files.indices) {
                    val image = if (files[i].isFile) {
                        R.drawable.file
                    } else {
                        R.drawable.folder
                    }
                    listFile.add(FileData(image, files[i].name, files[i].absolutePath))
                }
            } catch (e: NullPointerException) {
                showToast("Permission denied.")
                return
            }
        }
        binding.tvFilePath.text = path
        binding.rvListFiles.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val fileAdapter = FileAdapter(listFile)
        fileAdapter.onItemClick = {
            this.listFiles(it.absolutePath)
        }
        binding.rvListFiles.adapter = fileAdapter
    }

    fun showToast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }
}