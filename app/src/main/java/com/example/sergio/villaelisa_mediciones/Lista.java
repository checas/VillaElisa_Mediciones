package com.example.sergio.villaelisa_mediciones;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Lista extends AppCompatActivity {

    ListView lista;
    String[] arreglo;
    public int itemSeleccionado = -1;
    TextView tvEstado, tvSector;
    String sector = "";
    String verLista = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Intent intent = getIntent();
        sector = intent.getStringExtra("sector");
        verLista = intent.getStringExtra("ver");
        if(verLista.equals("1") != true) verLista = "0";

        tvEstado = findViewById(R.id.TV_State);
        if(sector.equals("V Elisa")) tvEstado.setText("Villa Elisa");
        else tvEstado.setText(sector);
        tvSector = findViewById(R.id.Tv_Sector);
        tvSector.setText("Sector " + sector);
        lista = findViewById(R.id.Lista);
        lista.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ordenModifica = Integer.parseInt(arreglo[position].split("\n")[0]);
                Intent intent = new Intent(Lista.this, Medir.class);
                intent.putExtra("ordenModifica", ordenModifica);
                intent.putExtra("sector", sector);
                intent.putExtra("ver",verLista);

                startActivity(intent);
            }
        });
        CargarLista(sector);

    }

    @Override
    protected void onResume() {
        super.onResume();
        CargarLista(sector);
    }

    public void CargarLista(String sector){
        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "DATOS", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getReadableDatabase();
        if (db != null) {
            String peridoActual = GetPeriodoActual();
            String aux = "";
            if(verLista.equals("1")){
                aux = "Select Conexiones_Orden, Conexiones_Cliente, Conexiones_Direccion FROM " +
                        "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual +"\"" + " AND Conexiones_Sector" +
                        " = \"" + sector +"\"" + "AND Conexiones_Medido" + " = \"" + 1 +"\"" + " ORDER BY Conexiones_Orden ASC";
            }else{
                aux = "Select Conexiones_Orden, Conexiones_Cliente, Conexiones_Direccion FROM " +
                        "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual +"\"" + " AND Conexiones_Sector" +
                        " = \"" + sector +"\"" + "AND Conexiones_Medido" + " = \"" + 0 +"\"" + " ORDER BY Conexiones_Orden ASC";
            }

            try {
                Cursor c = db.rawQuery(aux, null);
                int cantidad = c.getCount();
                int i = 0;
                arreglo = new String[cantidad];
                if (c != null) {
                    if (c.moveToFirst()) {
                        do{
                            String linea = c.getInt(0) +"\n\t\t\t\t\t\t" +"Dir: " +c.getString(2)+ "\n\t\t\t\t\t\t" + "Cliente: " +c.getString(1);
                            arreglo[i] = linea;
                            i++;
                        }while (c.moveToNext());

                    } else {
                        Toast.makeText(Lista.this, "Sin resultados", Toast.LENGTH_SHORT).show();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,arreglo);
                    lista.setAdapter(adapter);
                }
                db.close();
            } catch (Exception e) {
                Toast.makeText(Lista.this, "Sin resultados", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se puede accceder a la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

    public String GetPeriodoActual(){
        String periodo = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
        return  periodo;
    }

}
