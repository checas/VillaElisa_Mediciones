package com.example.sergio.villaelisa_mediciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Medir extends AppCompatActivity {

    int id = -1, orden = -1;
    String sector = "";
    TextView tvNConexion, tvNombre, tvDireccion, tvSector;
    EditText etAnterior, etActual;
    String verMedido = "0";
    Button bDescargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medir);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */
        Intent intent = getIntent();
        orden = intent.getIntExtra("ordenModifica",-1);
        sector = intent.getStringExtra("sector");

        verMedido = intent.getStringExtra("ver");
        if(verMedido.equals("1") != true) verMedido = "0";

        //declaraciones
        tvSector = findViewById(R.id.Tv_SectorM);
        tvSector.setText("Sector " + sector);
        tvNConexion = findViewById(R.id.TV_NConexion);
        tvNombre = findViewById(R.id.TV_Cliente);
        tvDireccion = findViewById(R.id.TV_Direccion);
        etAnterior = findViewById(R.id.Et_Anterior);
        etActual = findViewById(R.id.Et_Actual);
        CargarDatos(orden, sector);
        bDescargar = findViewById(R.id.B_GuardarM);
        bDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mActual = 0;
                String sMActual = etActual.getText().toString();
                if(sMActual.equals("")){
                    Toast.makeText(Medir.this, "El valor actual no puede ser nulo", Toast.LENGTH_SHORT).show();
                }else{
                    mActual = Integer.parseInt(sMActual);
                    int mAnterior = Integer.parseInt(etAnterior.getText().toString());
                    if(mActual >= mAnterior){
                        boolean guardado = GuardarMedicion(mActual, id);
                        if(guardado == true){
                            boolean hayProximo = CargarSiguente();
                            if(hayProximo == true) CargarDatos(orden, sector);
                            else onBackPressed();
                        }
                    }else{
                        Toast.makeText(Medir.this, "El valor actual no puede ser menor que el anterior", Toast.LENGTH_SHORT).show();
                        etActual.setText("");
                    }
                }
            }
        });
    }

    public void CargarDatos(int orden, String sector){
        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "DATOS", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getReadableDatabase();
        if (db != null) {
            String peridoActual = GetPeriodoActual();
            String aux = "";
            if(verMedido.equals("1")){
                aux = "Select Conexiones_Id, Conexiones_Numero, Conexiones_Cliente, Conexiones_Direccion, Conexiones_Anterior," +
                        "Conexiones_Actual FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual +"\"" +
                        " AND Conexiones_Sector" + " = \"" + sector +"\"" + "AND Conexiones_Medido" + " = \"" + 1 +"\"" +
                        "AND Conexiones_Orden" + " = \"" + orden +"\"";
            }else{
                aux = "Select Conexiones_Id, Conexiones_Numero, Conexiones_Cliente, Conexiones_Direccion, Conexiones_Anterior," +
                        "Conexiones_Actual FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual +"\"" +
                        " AND Conexiones_Sector" + " = \"" + sector +"\"" + "AND Conexiones_Medido" + " = \"" + 0 +"\"" +
                        "AND Conexiones_Orden" + " = \"" + orden +"\"";
            }
            try {
                Cursor c = db.rawQuery(aux, null);
                if (c != null) {
                    if (c.moveToFirst()) {
                        id = c.getInt(0);
                        int conexionN = c.getInt(1);
                        String cliente = c.getString(2);
                        String direccion = c.getString(3);
                        int anterior = c.getInt(4);
                        int actual = c.getInt(5);
                        CargarValores(conexionN, cliente, direccion, anterior, actual);
                    } else {
                        Toast.makeText(Medir.this, "Sin resultados", Toast.LENGTH_SHORT).show();
                    }
                }
                db.close();
            } catch (Exception e) {
                Toast.makeText(Medir.this, "Sin resultados", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se puede accceder a la base de datos", Toast.LENGTH_SHORT).show();
        }

    }

    public String GetPeriodoActual(){
        String periodo = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
        return  periodo;
    }

    public void CargarValores(int nConexion, String nombre, String domicilio, int anterior, int actual){
        tvNConexion.setText(""+nConexion);
        tvNombre.setText(nombre);
        tvDireccion.setText(domicilio);
        etAnterior.setText(""+anterior);
        if(actual == 0) etActual.setText("");
        else etActual.setText(""+actual);
    }

    public boolean GuardarMedicion(int mActual, int idAGrabar){
        boolean guardado = false;
        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "DATOS", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getWritableDatabase();
        if(db != null){
            ContentValues registroupdate = new ContentValues();
            registroupdate.put("Conexiones_Actual", mActual);
            registroupdate.put("Conexiones_Medido", 1);
            long i = db.update("conexiones",registroupdate,"Conexiones_Id="+idAGrabar,null);
            if(i>0){
                Toast.makeText(this, "Medición guardada", Toast.LENGTH_SHORT).show();
                guardado = true;
            } else guardado = false;
            db.close();
        }
        return guardado;
    }

    public boolean CargarSiguente(){
        boolean respuesta = false;
        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "DATOS", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getReadableDatabase();
        if (db != null) {
            String peridoActual = GetPeriodoActual();
            String aux = "Select Conexiones_Orden FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual +"\"" +
                    " AND Conexiones_Sector" + " = \"" + sector +"\"" + "AND Conexiones_Medido" + " = \"" + 0 +"\"" +
                    " ORDER BY Conexiones_Orden ASC";
            try {
                Cursor c = db.rawQuery(aux, null);
                if (c != null) {
                    if (c.moveToFirst()) {
                        orden = c.getInt(0);
                        respuesta = true;
                    } else {
                        respuesta = false;
                    }
                }
                db.close();
            } catch (Exception e) {
                Toast.makeText(Medir.this, "Sin resultados", Toast.LENGTH_SHORT).show();
                respuesta = false;
            }
        } else {
            Toast.makeText(this, "No se puede accceder a la base de datos", Toast.LENGTH_SHORT).show();
        }
        return respuesta;
    }
}
