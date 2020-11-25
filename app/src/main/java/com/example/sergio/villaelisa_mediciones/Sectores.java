package com.example.sergio.villaelisa_mediciones;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class Sectores extends AppCompatActivity {

    String usuario;
    TextView tvFecha, tvEstado;
    CheckBox cB0, cBA, cBAberastain, cBB, cBC, cBJardines, cBSalas, cBStBarbara, cBZaldivar;
    Button btVolver, btDescargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectores);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*  Get extras
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                usuario = null;
            } else {
                usuario = extras.getString("user_logeo");
                tvFecha = findViewById(R.id.TV_Fecha);
                tvFecha.setText(usuario);
            }
        } else {
            usuario = (String) savedInstanceState.getSerializable("user_logeo");
        }
        */
        //Check Boxs
        cB0 = findViewById(R.id.CBD0);
        cBA = findViewById(R.id.CBDA);
        cBAberastain = findViewById(R.id.CBDAberastain);
        cBB = findViewById(R.id.CBDB);
        cBC = findViewById(R.id.CBDC);
        cBJardines = findViewById(R.id.CBDJardines);
        cBSalas = findViewById(R.id.CBDSalas);
        cBStBarbara = findViewById(R.id.CBDStBarbara);
        cBZaldivar = findViewById(R.id.CBDZaldivar);
        //Text Views
        tvFecha = findViewById(R.id.TV_Fecha);
        tvFecha.setText("Descargar sectores");
        tvEstado = findViewById(R.id.TV_Estado);
        tvEstado.setText("Descargas disponibles");

        //botones
        btVolver = findViewById(R.id.BtVolver);
        btDescargar = findViewById(R.id.BtDescargar);

        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sectores.super.onBackPressed();
            }
        });

        InicioSectores();
        //Listeners de los check boxs
        cB0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });
        cBA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });
        cBAberastain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });
        cBB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });
        cBC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });
        cBJardines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });
        cBSalas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });
        cBStBarbara.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });
        cBZaldivar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EstadoBoton();
            }
        });

    }


    public void InicioSectores(){
        //inicio de estado de los check boxs
        cB0.setChecked(false);
        cBA.setChecked(false);
        cBAberastain.setChecked(false);
        cBB.setChecked(false);
        cBC.setChecked(false);
        cBJardines.setChecked(false);
        cBSalas.setChecked(false);
        cBStBarbara.setChecked(false);
        cBZaldivar.setChecked(false);
    }

    public void EstadoBoton(){
        if(cB0.isChecked() == false && cBA.isChecked() == false && cBAberastain.isChecked() == false &&
                cBB.isChecked() == false && cBC.isChecked() == false && cBJardines.isChecked() == false &&
                cBSalas.isChecked() == false && cBStBarbara.isChecked() == false && cBZaldivar.isChecked() == false){
            btVolver.setVisibility(View.VISIBLE);
            btDescargar.setVisibility(View.GONE);
        } else {

            btVolver.setVisibility(View.GONE);
            btDescargar.setVisibility(View.VISIBLE);
        }
    }

}
