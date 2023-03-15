package com.yuvesh.womensafety

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(
    context: Context,
    DATABASE_NAME: String = "mylist.db",
    val TABLE_NAME: String = "mylist_data",
    private val ID: String = "id",
    private val PHONE:String="phone"
) :
    SQLiteOpenHelper(context,DATABASE_NAME, null,1) {



    override fun onCreate(db: SQLiteDatabase?) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                PHONE + " NUMBER"+")")


        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun addName(std:StudentModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(PHONE, std.PHONE)
        values.put(ID,std.id)
        val success = db.insert(TABLE_NAME, null, values)

        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getName(): ArrayList<StudentModel> {
        val stdList: ArrayList<StudentModel> = ArrayList()
        val db = readableDatabase
        val selectQuery = "SELECT  * FROM" + TABLE_NAME
        val cursor: Cursor?

        try {
            cursor=db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)

            return ArrayList()

        }
        var id: Int
        var phone: String

        if (cursor.moveToFirst()) {

            do{
                 id=cursor!!.getInt(cursor.getColumnIndex("id"))
                 phone=cursor!!.getString(cursor.getColumnIndex("phone"))

                val std=StudentModel(id=id, PHONE= phone)
                stdList.add(std)
            }while (cursor.moveToNext())
        }
return stdList

    }

    fun updateData(std: StudentModel):Int
    {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(PHONE, std.PHONE)
        values.put(ID,std.id)
        val success = db.update(TABLE_NAME,values,"id="+std.id,null)

        db.close()
        return success
    }

    fun deleteData(id:Int): Int
    {
        val db = this.writableDatabase
        val values = ContentValues()


        values.put(ID,id)
        val success: Int = db.delete(TABLE_NAME,ID+="=?",null)

        db.close()
        return success
    }

}