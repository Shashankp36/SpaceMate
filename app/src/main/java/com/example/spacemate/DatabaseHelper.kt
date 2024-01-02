package com.example.spacemate



import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context : Context, factory : SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context , DATABASE_NAME , factory , DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_col + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_col + " TEXT," +
                BNAME_col + " TEXT," +
                Category_col + " TEXT," +
                House_name_col + " TEXT," +
                Location_col + " TEXT," +
                Rent_col + " TEXT," +
                Image_col + " BLOB," +
                Bathroom_col + " TEXT," +
                Bedroom_col + " TEXT," +
                ele_col + " TEXT," +
                geyser_col + " TEXT," +
                water_col + " TEXT," +
                parking_col + " TEXT," +
                wifi_col + " TEXT" + ")")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME )
        onCreate(db)
    }
    fun addName(name : String?,bName : String ,category : String,hName : String,location : String,rent : String ,
                imageBytes: ByteArray,
                bathroom : Int,
                bedroom : Int,
                ele : Int ,
                geyser : Int,
                water : Int,
                parking : Int,
                wifi: Int){
        val values = ContentValues()
        values.put(NAME_col ,name)
        values.put(BNAME_col ,bName)
        values.put(Category_col ,category)
        values.put(House_name_col ,hName)
        values.put(Location_col ,location)
        values.put(Rent_col ,rent)
        values.put(Image_col, imageBytes)
        values.put(Bathroom_col ,bathroom)
        values.put(Bedroom_col ,bedroom)
        values.put(ele_col ,ele)
        values.put(geyser_col ,geyser)
        values.put(water_col ,water)
        values.put(parking_col ,parking)
        values.put(wifi_col ,wifi)
        val db = this.writableDatabase
        db.insert(TABLE_NAME , null , values)
        db.close()
    }
    fun getName():Cursor{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME , null)
    }


    fun getDataByHouseName(houseName: String): Cursor {
        val db = this.readableDatabase
        return db.query(
            TABLE_NAME,
            null,
            "$House_name_col = ?",
            arrayOf(houseName),
            null,
            null,
            null
        )
    }
    fun getDataByCategory(category: String?): Cursor {
        val db = this.readableDatabase
        return db.query(
            TABLE_NAME,
            null,
            "$Category_col = ?",
            arrayOf(category),
            null,
            null,
            null
        )
    }

    fun delete(houseName: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$House_name_col = ?", arrayOf(houseName))
        db.close()
    }
    /*fun delete(hNa  : String ){

        val db = this.readableDatabase

        val query = ("DELETE FROM $TABLE_NAME WHERE hName == $hNa")


        db.execSQL(query)
    }*/
    companion object{
        private val DATABASE_NAME = "CSE226"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "my_table1"
        val ID_col = "id"
        val NAME_col = "name"
        val House_name_col = "hName"
        val Location_col = "location"
        val Rent_col = "rent"
        val BNAME_col = "bName"
        val ele_col = "electricity"
        val geyser_col = "geyser"
        val water_col = "water"
        val parking_col = "parking"
        val wifi_col = "wifi"
        val Image_col = "image"
        val Bedroom_col = "bedroom"
        val Bathroom_col = "bathroom"
        val Category_col = "category"


    }
}