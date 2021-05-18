package com.example.notescontentprovider.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class NotesDatabaseHelper(
    context: Context
): SQLiteOpenHelper(context, "notesDatabase", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE $TABLE_NAME (" +
                "$_ID INTEGER NOT NULL PRIMARY KEY, " +
                "$COLUMN_TITLE TEXT NOT NULL, " +
                "$COLUMN_DESCRIPTION TEXT NOT NULL)"
        db?.execSQL(sql)

    }

    override fun onUpgrade(db:SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        const val TABLE_NAME: String = "Notes"
        const val COLUMN_TITLE: String = "title"
        const val COLUMN_DESCRIPTION: String = "description"
    }
}