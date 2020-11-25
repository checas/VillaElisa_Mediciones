package com.example.sergio.villaelisa_mediciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sergio on 6/11/2017.
 */

public class ConexionesDBHelper extends SQLiteOpenHelper {
    String tabla = "CREATE TABLE conexiones(Conexiones_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Conexiones_Sector Text, Conexiones_Orden INTEGER, Conexiones_Numero INTEGER, " +
            "Conexiones_Cliente Text," + "Conexiones_Direccion Text, Conexiones_Anterior INTEGER," +
            "Conexiones_Actual INTEGER, Conexiones_Medido INTEGER, Conexiones_Periodo Text, " +
            "Conexiones_Habilitado INTEGER, Conexiones_Borrado INTEGER)";

    public ConexionesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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

