package com.example.sergio.villaelisa_mediciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yo_ch on 26/2/2018.
 */

public class SubidosDBHelper extends SQLiteOpenHelper {
    String tabla = "CREATE TABLE subidos(Subidos_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Subidos_Sector Text, Subidos_Periodo Text)";


    public SubidosDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
