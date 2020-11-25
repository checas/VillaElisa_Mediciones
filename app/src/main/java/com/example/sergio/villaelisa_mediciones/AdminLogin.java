package com.example.sergio.villaelisa_mediciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminLogin extends AppCompatActivity {

    TextView tvIp1, tvIp2, tvIp3, tvIp4;
    RadioButton rbA, rbB;
    Button guardar, bSubido, bTabla, bIngresar, bBackUp;
    String ipDB = null, tabletDB = null, tabletActual = "";
    boolean eRbA = false, eRbB = false, hayBase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        //asigno elementos
        tvIp1 = findViewById(R.id.TV_Ip1);
        tvIp2 = findViewById(R.id.TV_Ip2);
        tvIp3 = findViewById(R.id.TV_Ip3);
        tvIp4 = findViewById(R.id.TV_Ip4);
        rbA = findViewById(R.id.RB_A);
        rbB = findViewById(R.id.RB_B);
        guardar = findViewById(R.id.B_Guardar);
        bSubido = findViewById(R.id.BorrarSubido);
        bTabla = findViewById(R.id.BorrarTabla);
        bIngresar = findViewById(R.id.B_Ingresar);
        bBackUp = findViewById(R.id.B_Backup);

        final Spinner spinner = findViewById(R.id.spinner);
        String[] letra = {"A","Aberanstain","B","C","David","Jardines del sur","Medina","Olmos","Salas","Santa Barbara","V Elisa","Zaldivar"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spiner_layout,letra));

        InicioConfig();

        rbA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true && eRbA == false){
                    tabletActual = "A";
                    eRbA = true;
                    eRbB = false;
                    rbB.setChecked(false);
                }
            }
        });
        rbB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true && eRbB == false){
                    tabletActual = "B";
                    eRbB = true;
                    eRbA = false;
                    rbA.setChecked(false);
                }
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChekeoDeDatos()){
                    boolean respuesta = GuardarConfig();
                    if(respuesta == true){
                        onBackPressed();
                    }
                }
                else {
                    Toast.makeText(AdminLogin.this, "Datos incompletos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bSubido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String periodo = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
                String sectorSubido = spinner.getSelectedItem().toString();

                ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(AdminLogin.this, "SUBIDO", null, 1);
                SQLiteDatabase db = conexionesDBHelper.getWritableDatabase();
                if(db != null){
                    String whereBorrar = "Subidos_Sector='"+ sectorSubido + "' AND " + " Subidos_Perido='" + periodo + "'";
                    //long i = db.delete("subidos", whereBorrar, null);
                    //long i = db.insert("subidos", null, registronuevo);
                    int borrado =  db.delete("subidos", "Subidos_Sector=?", new String[]{sectorSubido});
                    if(borrado == 1){
                        Toast.makeText(AdminLogin.this, "Sector subido borrado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AdminLogin.this, "El sector no esta subido", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            }
        });

        bTabla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String periodo = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
                String sectorBorrar = spinner.getSelectedItem().toString();

                ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(AdminLogin.this, "DATOS", null, 1);
                SQLiteDatabase db = conexionesDBHelper.getWritableDatabase();

                if(db != null){
                    String whereBorrar = "Conexiones_Sector='"+ sectorBorrar + "' AND " + " Conexiones_Periodo='" + periodo + "'";
                    //long i = db.delete("subidos", whereBorrar, null);
                    //long i = db.insert("subidos", null, registronuevo);
                    int borrado =  db.delete("conexiones", "Conexiones_Sector=?", new String[]{sectorBorrar});
                    if(borrado != 0){
                        Toast.makeText(AdminLogin.this, "Sector subido borrado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AdminLogin.this, "El sector no esta cargado", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            }
        });

        bIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLogin.this,MenuPrincipal.class);
                intent.putExtra("user_logeo","admin");
                intent.putExtra("ipActual",ipDB);
                intent.putExtra("tabletActual",tabletDB);
                startActivity(intent);
            }
        });

        bBackUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();
                String pathData = "//data//"+getPackageName()+"//databases//"+"DATOS"+"";
                String backupDBPath = "backupDATOS.db";
                File currentDB = new File(data, pathData);
                File backupDB = new File(sd, backupDBPath);
                if (currentDB.exists()) {
                    FileChannel src = null;
                    try {
                        src = new FileInputStream(currentDB).getChannel();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    FileChannel dst = null;
                    try {
                        dst = new FileOutputStream(backupDB).getChannel();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        dst.transferFrom(src, 0, src.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        src.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        dst.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void InicioConfig(){
        ConfiguracionesDBHelper configuracionesDBHelper = new ConfiguracionesDBHelper(this, "CONFIG",null,1);
        SQLiteDatabase db = configuracionesDBHelper.getReadableDatabase();
        if(db != null) {
            String aux = "Select Configuraciones_Ip, Configuraciones_Tablet FROM " + "configuraciones" + " WHERE " + "Configuraciones_Id" + " = \"" + 1 + "\"";
            try{
                Cursor c = db.rawQuery(aux, null);
                if(c != null){
                    if(c.moveToFirst()){
                        ipDB = c.getString(0);
                        tabletDB = c.getString(1);
                        CargarCofig(ipDB, tabletDB);
                        hayBase = true;
                        Toast.makeText(this, "Datos leidos", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Dispositivo sin cofigurar", Toast.LENGTH_SHORT).show();
                        hayBase = false;
                    }
                }
                //db.close();
            }catch (Exception e){
                Toast.makeText(this, "Dispositivo sin cofigurar", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }else{
            Toast.makeText(this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void CargarCofig(String ip, String letraT){
        switch (letraT){
            case "A":
                tabletActual = letraT;
                eRbA = true;
                eRbB = false;
                rbA.setChecked(true);
                rbB.setChecked(false);
                break;
            case "B":
                tabletActual = letraT;
                eRbB = true;
                eRbA = false;
                rbB.setChecked(true);
                rbA.setChecked(false);
                break;
            default:
                //informar al usuario del errror?
                tabletActual = "";
                eRbA = false;
                eRbB = false;
                rbA.setChecked(false);
                rbB.setChecked(false);
        }
        String[] ipLeida = ip.split("\\.");
        if(ipLeida.length == 0 || ipLeida.length<3){
            //error en el dato leido
        }else{
            tvIp1.setText(ipLeida[0]);
            tvIp2.setText(ipLeida[1]);
            tvIp3.setText(ipLeida[2]);
            tvIp4.setText(ipLeida[3]);
        }
    }

    public boolean GuardarConfig(){
        boolean respuesta = false;

        ConfiguracionesDBHelper configuracionesDBHelper = new ConfiguracionesDBHelper(this, "CONFIG",null,1);
        SQLiteDatabase db = configuracionesDBHelper.getWritableDatabase();
        if(db != null){
            ContentValues registronuevo = new ContentValues();
            //registronuevo.put("Configuraciones_Id", 1);
            registronuevo.put("Configuraciones_Ip", GetIpNueva());
            registronuevo.put("Configuraciones_Tablet", tabletActual);
            try {
                if(hayBase == false){
                    long i = db.insert("configuraciones", null, registronuevo);
                    if(i>0){
                        Toast.makeText(this, "Configuraciones actualizados", Toast.LENGTH_SHORT).show();
                        respuesta = true;
                    } else {
                        Toast.makeText(this, "No se actualizaron las configuraciones", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    db.update("configuraciones", registronuevo, "Configuraciones_Id" + "= ?", new String[]{"1"});
                    Toast.makeText(this, "Configuraciones actualizados", Toast.LENGTH_SHORT).show();
                    respuesta = true;
                }
                db.close();
            }catch (Exception e){
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        }
        return respuesta;
    }

    public String GetIpNueva(){
        String ipNueva = tvIp1.getText() + "."+tvIp2.getText() + "." + tvIp3.getText() + "." + tvIp4.getText();
        return  ipNueva;
    }
    public boolean ChekeoDeDatos(){
        if(tvIp1.getText().toString().equals("") || tvIp2.getText().toString().equals("") ||
                tvIp3.getText().toString().equals("") || tvIp4.getText().toString().equals("") ||
                (eRbA == false && eRbB == false)){
            return false;
        }else return true;
    }
}
