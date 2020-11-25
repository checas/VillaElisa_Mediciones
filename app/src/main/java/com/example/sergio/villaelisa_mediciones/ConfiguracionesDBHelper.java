package com.example.sergio.villaelisa_mediciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yo_ch on 18/2/2018.
 */

public class ConfiguracionesDBHelper extends SQLiteOpenHelper {
    String tabla = "CREATE TABLE configuraciones(Configuraciones_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Configuraciones_Ip Text, Configuraciones_Tablet Text)";

    public ConfiguracionesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
