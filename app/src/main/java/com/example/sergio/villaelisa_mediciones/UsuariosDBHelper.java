package com.example.sergio.villaelisa_mediciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sergio on 5/11/2017.
 */

public class UsuariosDBHelper extends SQLiteOpenHelper {
    String tabla = "CREATE TABLE usuarios(Usuarios_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Usuarios_User Text, Usuarios_Pass Text, Usuarios_Borrado INTEGER)";
    public UsuariosDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
