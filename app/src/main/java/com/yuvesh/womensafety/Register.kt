package com.yuvesh.womensafety

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Register : AppCompatActivity() {

    lateinit var et:EditText
    lateinit var b1:Button
    lateinit var b3:Button
    lateinit var b4:Button
    lateinit var listView: ListView
    lateinit var mydb:DatabaseHandler
    lateinit var sqLiteOpenHelper: SQLiteOpenHelper
    lateinit var sqLiteDatabase: SQLiteDatabase
    lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter?=null
    private var std:StudentModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        et = findViewById(R.id.etPhone)
        b1 = findViewById(R.id.Add)
        b3 = findViewById(R.id.View)
        b4=findViewById(R.id.update)

        recyclerView=findViewById(R.id.recyclerview)

        mydb= DatabaseHandler(this@Register)

        recyclerView()

        b1.setOnClickListener{
            addData()

        }

        b3.setOnClickListener {
            getData()

        }
        adapter?.setOnClickItem {
            Toast.makeText(this@Register,it.PHONE,Toast.LENGTH_SHORT).show()
            et.setText(it.PHONE)
            std=it
        }

        adapter?.setOnClickDeleteItem {
            delete(it.id)
        }

    }

    private fun recyclerView()
    {
        recyclerView.layoutManager=LinearLayoutManager(this@Register)
        adapter= StudentAdapter()
        recyclerView.adapter=adapter
    }

    private fun delete(id:Int)
    {
          if(id==null)
              return

        val builder=AlertDialog.Builder(this@Register)
        builder.setMessage("Are you sure you want to delete")
        builder.setCancelable(true)
        builder.setPositiveButton("yes"){
            dialog,_->
            mydb.deleteData(id)
            getData()
            dialog.dismiss()
        }
        builder.setNegativeButton("No")
        {
            dialog,_->

            dialog.dismiss()
        }

        val alert= builder.create()
        alert.show()

    }

    private fun updateData()
    {
        val phone=et.text.toString()
        if(phone==std?.PHONE) {
            Toast.makeText(this@Register, "record not changed", Toast.LENGTH_LONG).show()
            return
        }

        if(std==null)
            return

        val std=StudentModel(id=std!!.id, PHONE=phone)
        val status=mydb.updateData(std)

        if(status>-1)
        {
            Toast.makeText(this@Register,"Student added",Toast.LENGTH_LONG).show()
            et.setText("")
            et.requestFocus()
            getData()
        }
        else
        {
            Toast.makeText(this@Register,"Not updated",Toast.LENGTH_SHORT).show()
        }

    }

    private fun getData() {
        val stdList= mydb.getName()

        Log.e("pppp","${stdList.size}")
        adapter?.additems(stdList)
            }


    private fun addData() {
       val phone=et.text.toString()
        if(phone.isEmpty())
        {
            Toast.makeText(this@Register,"Enter required field",Toast.LENGTH_LONG).show()
        }
        else
        {
            val std=StudentModel(PHONE = phone)
            val status=mydb.addName(std)

            if(status>-1)
            {
                Toast.makeText(this@Register,"Student added",Toast.LENGTH_LONG).show()
                et.setText("")
                et.requestFocus()
                getData()
            }
            else
            {
                Toast.makeText(this@Register,"Record not saved",Toast.LENGTH_SHORT).show()
            }
        }
    }
}