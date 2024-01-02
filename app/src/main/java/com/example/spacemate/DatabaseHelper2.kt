package com.example.spacemate



import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper2(context : Context, factory : SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context , DATABASE_NAME , factory , DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_col + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Owner_name_col + " TEXT," +
                Booker_email_col + " TEXT," +
                Booker_phn_col + " TEXT," +
                Booker_name_col + " TEXT," +
                House_name_col + " TEXT," +
                Location_col + " TEXT," +

                Rent_col + " TEXT" + ")")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME )
        onCreate(db)
    }
    fun addName(Oname : String,
                Bmail : String? ,
                Bphn : String?,
                Bname : String?,
                hName : String,
                location : String,
                rent : String
                ){
        val values = ContentValues()
        values.put(Owner_name_col ,Oname)
        values.put(Booker_email_col ,Bmail)
        values.put(Booker_phn_col , Bphn)
        values.put(Booker_name_col ,Bname)
        values.put(House_name_col ,hName)
        values.put(Location_col ,location)
        values.put(Rent_col ,rent)
        val db = this.writableDatabase
        db.insert(TABLE_NAME , null , values)
        db.close()
    }
    fun getName():Cursor{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME , null)
    }



    fun getDataByOwner(name: String?): Cursor {
        val db = this.readableDatabase
        if (db != null && db.isOpen) {
            Log.d("DatabaseHelper2", "Database opened successfully.")
        } else {
            Log.e("DatabaseHelper2", "Failed to open database.")
        }
        return db.query(
            TABLE_NAME,
            null,
            "$Owner_name_col = ?",
            arrayOf(name),
            null,
            null,
            null
        )
    }
    /*fun delete(houseName: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$House_name_col = ?", arrayOf(houseName))
        db.close()
    }*/


    fun delete(ag  : String ){

        val db = this.readableDatabase
        //db.delete(TABLE_NAME, "age=?", arrayOf(ageValue))
        // db.close()

        val query = ("DELETE FROM $TABLE_NAME WHERE age == $ag")


        db.execSQL(query)
    }
    companion object{
        private val DATABASE_NAME = "Mine"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "my_table2"
        val ID_col = "id"
        val Owner_name_col = "name"
        val Booker_name_col = "bname"
        val House_name_col = "hName"
        val Location_col = "location"
        val Rent_col = "rent"
        val Category_col = "category"
        val Booker_email_col = "email"
        val Booker_phn_col = "phone"


    }
}