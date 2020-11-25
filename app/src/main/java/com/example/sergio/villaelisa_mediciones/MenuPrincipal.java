package com.example.sergio.villaelisa_mediciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shinelw.library.ColorArcProgressBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MenuPrincipal extends AppCompatActivity {

    String usuario, ip, tablet;
    Button bDescargar;
    boolean pedidoSector = false, pedidoSubir = false;
    //boolean sA = false, sAberastain = false, sJardines = false, sMedina = false, SStBarbara = false, sVElisa = false;
    Button verA, verAberastain, verJardines, verMedina, verSalas, verStBarbara, verVElisa;
    Button subirA, subirAberasatain, subirJardines, subirMedina, subirSalas, subirStBarbara, subirVElisa;
    Button verB, verC, verDavid, verOlmos, verZaldivar;
    Button subirB, subirC, subirDavid, subirOlmos, subirZaldivar;
    TextView tvA, tvAberastain, tvJardines, tvMedina, tvSalas, tvStBarbara, tvVElisa;
    TextView tvB, tvC, tvDavid, tvOlmos, tvZaldivar;

    @Override
    protected void onResume() {
        super.onResume();
        ConfigInicial();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final LinearLayout LInicio = findViewById(R.id.layoutInicio);
        final LinearLayout LSector = findViewById(R.id.layoutSector);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                usuario = null;
            } else {
                usuario = extras.getString("user_logeo");
                ip = extras.getString("ipActual");
                tablet = extras.getString("tabletActual");
            }
        } else {
            usuario = (String) savedInstanceState.getSerializable("user_logeo");
            ip = (String) savedInstanceState.getSerializable("ipActual");
            tablet = (String) savedInstanceState.getSerializable("tabletActual");
        }
        //spinner
        /*
        Spinner spinner = findViewById(R.id.spinner);
        String[] letra = {"A","B","C","D","E"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spiner_layout,letra));
        */
        //switch

        /*final Switch cambio = findViewById(R.id.cambio);
        cambio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked== true){
                    LSector.setVisibility(View.VISIBLE);
                    LInicio.setVisibility(View.GONE);
                }else{
                    LInicio.setVisibility(View.VISIBLE);
                    LSector.setVisibility(View.GONE);
                }
            }
        });
        */
        verA = findViewById(R.id.BtVerA);
        verAberastain = findViewById(R.id.BtVerAberastain);
        verJardines = findViewById(R.id.BtVerJardines);
        verMedina = findViewById(R.id.BtVerMedina);
        verSalas = findViewById(R.id.BtVerSalas);
        verStBarbara = findViewById(R.id.BtVerStBarbara);
        verVElisa = findViewById(R.id.BtVerVElisa);

        subirA = findViewById(R.id.BtSubirA);
        subirAberasatain = findViewById(R.id.BtSubirAberastain);
        subirJardines = findViewById(R.id.BtSubirJardines);
        subirMedina = findViewById(R.id.BtSubirMedina);
        subirSalas = findViewById(R.id.BtSubirSalas);
        subirStBarbara = findViewById(R.id.BtSubirStBarbara);
        subirVElisa = findViewById(R.id.BtSubirVElisa);

        tvA = findViewById(R.id.TV_A);
        tvAberastain = findViewById(R.id.TV_Aberastain);
        tvJardines = findViewById(R.id.TV_Jardines);
        tvMedina = findViewById(R.id.TV_Medina);
        tvSalas = findViewById(R.id.TV_Salas);
        tvStBarbara = findViewById(R.id.TV_StBarbara);
        tvVElisa = findViewById(R.id.TV_VElisa);

        verB = findViewById(R.id.BtVerB);
        verC = findViewById(R.id.BtVerC);
        verDavid = findViewById(R.id.BtVerDavid);
        verOlmos = findViewById(R.id.BtVerOlmos);
        verZaldivar = findViewById(R.id.BtVerZaldivar);

        subirB = findViewById(R.id.BtSubirB);
        subirC = findViewById(R.id.BtSubirC);
        subirDavid = findViewById(R.id.BtSubirDavid);
        subirOlmos = findViewById(R.id.BtSubirOlmos);
        subirZaldivar = findViewById(R.id.BtSubirZaldivar);

        tvB = findViewById(R.id.TV_B);
        tvC = findViewById(R.id.TV_C);
        tvDavid = findViewById(R.id.TV_David);
        tvOlmos = findViewById(R.id.TV_Olmos);
        tvZaldivar = findViewById(R.id.TV_Zaldivar);

        bDescargar = findViewById(R.id.BtDescargar);

        SetFechaAcutal();
        ConfigInicial();

        //Boton principal
        bDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tablet.equals("A")){
                    BajarSectoresA();
                }else{
                    BajarSectoresB();
                }
            }
        });
        //botones sectores
        verA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","A");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verAberastain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","Aberanstain");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verJardines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","Jardines del sur");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verMedina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","Medina");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","Salas");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verStBarbara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","Santa Barbara");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verVElisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","V Elisa");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });

        verB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","B");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","C");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verDavid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","David");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verOlmos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","Asentamiento olmos");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        verZaldivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                intent.putExtra("sector","Zaldivar");
                intent.putExtra("ver","0");
                startActivity(intent);
            }
        });
        //botones subir datos
        subirA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("A");
                SectorSubido("A");
            }
        });
        subirAberasatain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("Aberanstain");
                SectorSubido("Aberanstain");
            }
        });
        subirJardines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("Jardines del sur");
                SectorSubido("Jardines del sur");
            }
        });
        subirMedina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("Medina");
                SectorSubido("Medina");
            }
        });
        subirSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("Salas");
                SectorSubido("Salas");
            }
        });
        subirStBarbara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("Santa Barbara");
                SectorSubido("Santa Barbara");
            }
        });
        subirVElisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("V Elisa");
                SectorSubido("V Elisa");
            }
        });
        subirB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("B");
                SectorSubido("B");
            }
        });
        subirC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("C");
                SectorSubido("C");
            }
        });
        subirDavid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("David");
                SectorSubido("David");
            }
        });
        subirOlmos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("Asentamiento olmos");
                SectorSubido("Asentamiento olmos");
            }
        });
        subirZaldivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean subido = SubirDatos("Zaldivar");
                SectorSubido("Zaldivar");
            }
        });

        //mostrar mediciones cuando ya se midieron

        tvA.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","A");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvAberastain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","Aberanstain");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvJardines.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","Jardines del sur");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvMedina.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","Medina");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvSalas.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","Salas");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvStBarbara.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","Santa Barbara");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvVElisa.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","V Elisa");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","B");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","C");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvDavid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","David");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvOlmos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","Asentamiento olmos");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

        tvZaldivar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(usuario.equals("admin")){
                    Intent intent = new Intent(MenuPrincipal.this,Lista.class);
                    intent.putExtra("sector","Zaldivar");
                    intent.putExtra("ver", "1");
                    startActivity(intent);
                }
                return false;
            }
        });

    }

    public void SetFechaAcutal(){
        TextView tvFecha = findViewById(R.id.TV_Fecha);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String[] fecha = date.split("-");
        String salida = EditDia(fecha[2]) + " de " + EditMes(fecha[1]) + " del " +fecha[0];

        tvFecha.setText(salida);
    }

    public String EditDia (String day){
        String dia;
        if(day.startsWith("0")){
            dia = day.substring(1);
        } else {
            dia = day;
        }
        return dia;
    }

    public String EditMes (String month){
        String mes = null;
        switch (month){
            case "01":
                mes = "Enero";
                break;
            case "02":
                mes = "Febrero";
                break;
            case "03":
                mes = "Marzo";
                break;
            case "04":
                mes = "Abril";
                break;
            case "05":
                mes = "Mayo";
                break;
            case "06":
                mes = "Junio";
                break;
            case "07":
                mes = "Julio";
                break;
            case "08":
                mes = "Agosto";
                break;
            case "09":
                mes = "Septiembre";
                break;
            case "10":
                mes = "Octubre";
                break;
            case "11":
                mes = "Noviembre";
                break;
            case "12":
                mes = "Diciembre";
                break;
            default:
                mes = "";
                break;
        }
        return mes;
    }

    public String GetPeriodoActual(){
        String periodo = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
        return  periodo;
    }

    public void ConfigInicial() {
        //ajusto el layout segun la tableta
        setLayout();

        //reviso si la base existe
        boolean existenSectores = false;
        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "DATOS", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getReadableDatabase();
        if (db != null) {
            //Existe base de datos
            String peridoActual = GetPeriodoActual();
            String aux = "";
            if(tablet.equals("A")){
                //busco solo si está bajado el sector A
                aux = "Select Conexiones_Id FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual + "\"" +
                        " AND Conexiones_Sector" + " = \"" + "A" + "\"";
            }else{
                //busco solo si está bajado el sector B
                aux = "Select Conexiones_Id FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual + "\"" +
                        " AND Conexiones_Sector" + " = \"" + "C" + "\"";
            }
            try {
                Cursor c = db.rawQuery(aux, null);
                if (c != null) {
                    if (c.moveToFirst()) {
                        existenSectores = true;
                    } else {
                        existenSectores = false;
                    }
                }
                db.close();
            } catch (Exception e) {
                existenSectores = false;
            }
        } else {
            Toast.makeText(this, "No se puede accceder a la base de datos", Toast.LENGTH_SHORT).show();
        }

        if(existenSectores == true){
            HaySectoresDescargados();
        } else {
            NoHaySectoresDescargados();
        }

    }

    public void setLayout(){
        LinearLayout layoutA = findViewById(R.id.LlA);
        LinearLayout layoutB = findViewById(R.id.LlB);
        if(tablet.equals("A")){
            layoutA.setVisibility(View.VISIBLE);
            layoutB.setVisibility(View.GONE);
        }else{
            layoutB.setVisibility(View.VISIBLE);
            layoutA.setVisibility(View.GONE);
        }
    }

    public void NoHaySectoresDescargados(){
        //Switch detallesSectores = findViewById(R.id.cambio);
        //detallesSectores.setVisibility(View.INVISIBLE);
        TextView estado = findViewById(R.id.TV_Estado);
        estado.setText("No hay datos de sectores descargados");
        bDescargar.setVisibility(View.VISIBLE);
    }

    public void HaySectoresDescargados(){
        bDescargar.setVisibility(View.INVISIBLE);
        TextView tvEstado = findViewById(R.id.TV_Estado);
        tvEstado.setVisibility(View.INVISIBLE);
        ProgressBar pbTotal = findViewById(R.id.PBTotal);
        //cargo los datos para cada sector
        if(tablet.equals("A")){
            //texview
            //TextView tvA = findViewById(R.id.TV_A);
            //TextView tvAberastain = findViewById(R.id.TV_Aberastain);
            //TextView tvJardines = findViewById(R.id.TV_Jardines);
            //TextView tvMedina = findViewById(R.id.TV_Medina);
            //TextView tvSalas = findViewById(R.id.TV_Salas);
            //TextView tvStBarbara = findViewById(R.id.TV_StBarbara);
            //TextView tvVElisa = findViewById(R.id.TV_VElisa);
            tvA.setEnabled(true);
            tvAberastain.setEnabled(true);
            tvJardines.setEnabled(true);
            tvMedina.setEnabled(true);
            tvSalas.setEnabled(true);
            tvStBarbara.setEnabled(true);
            tvVElisa.setEnabled(true);
            //botones
            verA.setEnabled(true);
            verAberastain.setEnabled(true);
            verJardines.setEnabled(true);
            verMedina.setEnabled(true);
            verSalas.setEnabled(true);
            verStBarbara.setEnabled(true);
            verVElisa.setEnabled(true);
            //declaracion de las progress bar
            ProgressBar pbA = findViewById(R.id.PBA);
            ProgressBar pbAberastain = findViewById(R.id.PBAberastain);
            ProgressBar pbJardines = findViewById(R.id.PBJardines);
            ProgressBar pbMedina = findViewById(R.id.PBMedina);
            ProgressBar pbSalas = findViewById(R.id.PBSalas);
            ProgressBar pbStBarbara = findViewById(R.id.PBStBarbara);
            ProgressBar pbVElisa = findViewById(R.id.PBVElisa);

            //busco cantidad en la tabla
            int cantA = CantidadMediciones("A",false);
            int cantAberastain = CantidadMediciones("Aberanstain",false);
            int cantJardines = CantidadMediciones("Jardines del sur",false);
            int cantMedina = CantidadMediciones("Medina",false);
            int cantSalas = CantidadMediciones("Salas",false);
            int cantStaBarbara = CantidadMediciones("Santa Barbara",false);
            int cantVElisa = CantidadMediciones("V Elisa",false);
            int cantTotal = cantA +  cantAberastain + cantJardines + cantMedina + cantSalas + cantStaBarbara + cantVElisa;
            int medA = CantidadMediciones("A",true);
            int medAberastain = CantidadMediciones("Aberanstain",true);
            int medJardines = CantidadMediciones("Jardines del sur",true);
            int medMedina = CantidadMediciones("Medina",true);
            int medSalas = CantidadMediciones("Salas",true);
            int medStBarabara = CantidadMediciones("Santa Barbara",true);
            int medVElisa = CantidadMediciones("V Elisa",true);
            int medTotal = medA + medAberastain +medJardines + medMedina + medSalas + medStBarabara + medVElisa;
            //habilito los sectores que se pueden subir
            if(cantA == medA) MostrarSubir("A");
            if(cantAberastain == medAberastain) MostrarSubir("Aberanstain");
            if(cantJardines == medJardines) MostrarSubir("Jardines del sur");
            if(cantMedina == medMedina) MostrarSubir("Medina");
            if(cantSalas == medSalas) MostrarSubir("Salas");
            if(cantStaBarbara == medStBarabara) MostrarSubir("Santa Barbara");
            if(cantVElisa == medVElisa) MostrarSubir("V Elisa");
            //seteo de progress bars
            pbTotal.setMax(cantTotal);
            pbTotal.setProgress(medTotal);
            pbA.setVisibility(View.VISIBLE);
            pbA.setMax(cantA);
            pbA.setProgress(medA);
            pbAberastain.setVisibility(View.VISIBLE);
            pbAberastain.setMax(cantAberastain);
            pbAberastain.setProgress(medAberastain);
            pbJardines.setVisibility(View.VISIBLE);
            pbJardines.setMax(cantJardines);
            pbJardines.setProgress(medJardines);
            pbMedina.setVisibility(View.VISIBLE);
            pbMedina.setMax(cantMedina);
            pbMedina.setProgress(medMedina);
            pbSalas.setVisibility(View.VISIBLE);
            pbSalas.setMax(cantSalas);
            pbSalas.setProgress(medSalas);
            pbStBarbara.setVisibility(View.VISIBLE);
            pbStBarbara.setMax(cantStaBarbara);
            pbStBarbara.setProgress(medStBarabara);
            pbVElisa.setVisibility(View.VISIBLE);
            pbVElisa.setMax(cantVElisa);
            pbVElisa.setProgress(medVElisa);
        }else{
            //text view
            //TextView tvB = findViewById(R.id.TV_B);
            //TextView tvC = findViewById(R.id.TV_C);
            //TextView tvDavid = findViewById(R.id.TV_David);
            //TextView tvOlmos = findViewById(R.id.TV_Olmos);
            //TextView tvZaldivar = findViewById(R.id.TV_Zaldivar);
            tvB.setEnabled(true);
            tvC.setEnabled(true);
            tvDavid.setEnabled(true);
            tvOlmos.setEnabled(true);
            tvZaldivar.setEnabled(true);
            //botones
            verB.setEnabled(true);
            verC.setEnabled(true);
            verDavid.setEnabled(true);
            verOlmos.setEnabled(true);
            verZaldivar.setEnabled(true);
            //declaracion de progress bar
            ProgressBar pbB = findViewById(R.id.PBB);
            ProgressBar pbC = findViewById(R.id.PBC);
            ProgressBar pbDavid = findViewById(R.id.PBDavid);
            ProgressBar pbOlmos = findViewById(R.id.PBOlmos);
            ProgressBar pbZaldivar = findViewById(R.id.PBZaldivar);
            //busco cantidad en la tabla
            int cantB = CantidadMediciones("B",false);
            int cantC = CantidadMediciones("C",false);
            int cantDavid = CantidadMediciones("David",false);
            int cantOlmos = CantidadMediciones("Asentamiento olmos",false);
            int cantZaldivar = CantidadMediciones("Zaldivar",false);
            int cantTotal = cantB + cantC + cantDavid +cantOlmos + cantZaldivar;
            int medB = CantidadMediciones("B",true);
            int medC = CantidadMediciones("C",true);
            int medDavid = CantidadMediciones("David",true);
            int medOlmos = CantidadMediciones("Asentamiento olmos",true);
            int medZaldivar = CantidadMediciones("Zaldivar",true);
            int medTotal = medB + medC + medDavid + medOlmos + medZaldivar;
            //habilito los sectores que se pueden subir
            if(cantB == medB) MostrarSubir("B");
            if(cantC == medC) MostrarSubir("C");
            if(cantDavid == medDavid) MostrarSubir("David");
            if(cantOlmos == medOlmos) MostrarSubir("Asentamiento olmos");
            if(cantZaldivar == medZaldivar) MostrarSubir("Zaldivar");
            //seteo de progress bars
            pbTotal.setMax(cantTotal);
            pbTotal.setProgress(medTotal);
            pbB.setVisibility(View.VISIBLE);
            pbB.setMax(cantB);
            pbB.setProgress(medB);
            pbC.setVisibility(View.VISIBLE);
            pbC.setMax(cantC);
            pbC.setProgress(medC);
            pbDavid.setVisibility(View.VISIBLE);
            pbDavid.setMax(cantDavid);
            pbDavid.setProgress(medDavid);
            pbOlmos.setVisibility(View.VISIBLE);
            pbOlmos.setMax(cantOlmos);
            pbOlmos.setProgress(medOlmos);
            pbZaldivar.setVisibility(View.VISIBLE);
            pbZaldivar.setMax(cantZaldivar);
            pbZaldivar.setProgress(medZaldivar);
        }

    }

    public int CantidadMediciones(String sector, boolean medido){
        int cantidad = 0;

        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "DATOS", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getReadableDatabase();
        if (db != null) {
            //Existe base de datos
            String peridoActual = GetPeriodoActual();
            String aux;
            if(sector.equals("todos")){
                if(medido == false){
                    aux = "Select Conexiones_Sector FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual + "\"";
                }else{
                    aux = "Select Conexiones_Sector FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual + "\"" +
                    " AND Conexiones_Medido" + " = \"" + 1 + "\"" ;
                }

            }else{
                if(medido == false){
                    aux = "Select Conexiones_Sector FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual + "\"" +
                            " AND Conexiones_Sector"+ " = \"" + sector + "\"" ;
                }else{
                    aux = "Select Conexiones_Sector FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" + " = \"" + peridoActual + "\"" +
                            " AND Conexiones_Sector"+ " = \"" + sector + "\"" + " AND Conexiones_Medido" + " = \"" + 1 + "\"";
                }

            }
            try {
                Cursor c = db.rawQuery(aux, null);
                if (c != null) {
                    if (c.moveToFirst()) {
                        cantidad = c.getCount();
                    } else {
                        cantidad = 0;
                    }
                }
                db.close();
            } catch (Exception e) {
                cantidad = -1;
            }
        } else {
            cantidad = -1;
        }
        return cantidad;
    }

    public void BajarSectoresA(){
        //
        BajarSector("A");
    }

    public void BajarSectoresB(){
        BajarSector("B");
    }

    public void BajarSector(final String tableta) {
        final boolean[] resultado = {false};

        String periodo = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
        String[] fecha = periodo.split("-");
        final String anio = fecha[0];
        final String mes = fecha[1];

        pedidoSector = false;

        final AsyncHttpClient client = new AsyncHttpClient();

        //String url = getString(R.string.ip) + "GetUsuarios.php";
        String url = "http://" + ip + "/" + "GetMediciones.php" + "/?mes="+ mes + "&anio="+ anio + "&tablet=" + tableta;
        client.post(url, null, new AsyncHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(pedidoSector == false){
                    pedidoSector = true;
                    if (statusCode == 200){
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(new String(responseBody));
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                int cantidad = jsonResponse.getInt("cantidad");
                                int conexionId = 0;
                                int orden = 0;
                                String sector = "";
                                String domicilio = "";
                                String nombre = "";
                                int anterior = 0;
                                int actual = 0;
                                int habilitado = 0;
                                int borrado = 0;
                                for (int i = 0; i < cantidad; i++) {
                                    String indice = Integer.toString(i);
                                    JSONObject datos = jsonResponse.getJSONObject(indice);
                                    conexionId = datos.getInt("conexionId");
                                    sector = datos.getString("sector");
                                    orden = datos.getInt("orden");
                                    domicilio = datos.getString("domicilio");
                                    nombre = datos.getString("nombre");
                                    anterior = datos.getInt("anterior");
                                    actual = datos.getInt("actual");
                                    habilitado = datos.getInt("habilitado");
                                    borrado = datos.getInt("borrado");
                                    InsertarEnDBSectores(sector,conexionId, orden, domicilio, nombre, anterior, actual, habilitado, borrado);
                                }
                                resultado[0] = true;
                                Toast.makeText(MenuPrincipal.this, "Conexiones descargadas", Toast.LENGTH_SHORT).show();
                                ConfigInicial();
                            }else{
                                Toast.makeText(MenuPrincipal.this, "No hay mediciones para el periodo actual", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MenuPrincipal.this, "Ocurrio un error de comunicación", Toast.LENGTH_SHORT).show();
                        }
                        //resultado[0] = true;
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(pedidoSector == false){
                    if(pedidoSector == false){
                        pedidoSector = true;
                        String errore = "Error: " + statusCode;
                        resultado[0] = false;
                        Toast.makeText(MenuPrincipal.this, "Error al descargar sectores", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if (pedidoSector == false){
                    resultado[0] = false;
                    pedidoSector = true;
                    Toast.makeText(MenuPrincipal.this, "Tardo mucho", Toast.LENGTH_SHORT).show();
                }
            }
        }, 10000);

    }
    public void InsertarEnDBSectores(String _sector,int conexionId, int orden, String domicilio, String nombre, int anterior,int actual, int habilitado, int borrado){
        String periodo = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());

        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "DATOS", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getWritableDatabase();
        if(db != null){
            ContentValues registronuevo = new ContentValues();
            registronuevo.put("Conexiones_Sector", _sector);
            registronuevo.put("Conexiones_Orden", orden);
            registronuevo.put("Conexiones_Numero", conexionId);
            registronuevo.put("Conexiones_Cliente", nombre);
            registronuevo.put("Conexiones_Direccion", domicilio);
            registronuevo.put("Conexiones_Anterior", anterior);
            registronuevo.put("Conexiones_Actual", actual);
            registronuevo.put("Conexiones_Medido", 0);
            registronuevo.put("Conexiones_Periodo", periodo);
            registronuevo.put("Conexiones_Habilitado", habilitado);
            registronuevo.put("Conexiones_Borrado", borrado);

            long i = db.insert("conexiones", null, registronuevo);
            if(i>0){
                //Toast.makeText(this, "Conexiones actualizadas", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void MostrarSubir(String sectorD){
        String periodo = GetPeriodoActual();
        boolean estado = false;
        SubidosDBHelper subidosDBHelper = new SubidosDBHelper(this, "SUBIDO",null,1);
        SQLiteDatabase db = subidosDBHelper.getReadableDatabase();
        if(db != null) {
            String aux = "Select Subidos_Id FROM " + "subidos" + " WHERE " + "Subidos_Sector" + " = \"" + sectorD + "\"" +
            " AND Subidos_Periodo" + " = \"" + periodo + "\"";
            try{
                Cursor c = db.rawQuery(aux, null);
                if(c != null){
                    if(c.moveToFirst()){
                        MostrarBSubir(sectorD, false);
                    }else{
                        MostrarBSubir(sectorD, true);
                    }
                }
                //db.close();
            }catch (Exception e){
                Toast.makeText(this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void MostrarBSubir(String sectorM, boolean estado){
        switch (sectorM){
            //tablet A
            case "A":
                verA.setVisibility(View.GONE);
                subirA.setVisibility(View.VISIBLE);
                if(estado == true) subirA.setEnabled(true);
                else subirA.setEnabled(false);
                break;
            case "Aberanstain":
                verAberastain.setVisibility(View.GONE);
                subirAberasatain.setVisibility(View.VISIBLE);
                if(estado == true) subirAberasatain.setEnabled(true);
                else subirAberasatain.setEnabled(false);
                break;
            case "Jardines del sur":
                verJardines.setVisibility(View.GONE);
                subirJardines.setVisibility(View.VISIBLE);
                if(estado == true) subirJardines.setEnabled(true);
                else subirJardines.setEnabled(false);
                break;
            case "Medina":
                verMedina.setVisibility(View.GONE);
                subirMedina.setVisibility(View.VISIBLE);
                if(estado == true) subirMedina.setEnabled(true);
                else subirMedina.setEnabled(false);
                break;
            case "Salas":
                verSalas.setVisibility(View.GONE);
                subirSalas.setVisibility(View.VISIBLE);
                if(estado == true) subirSalas.setEnabled(true);
                else subirSalas.setEnabled(false);
                break;
            case "Santa Barbara":
                verStBarbara.setVisibility(View.GONE);
                subirStBarbara.setVisibility(View.VISIBLE);
                if(estado == true) subirStBarbara.setEnabled(true);
                else subirStBarbara.setEnabled(false);
                break;
            case "V Elisa":
                verVElisa.setVisibility(View.GONE);
                subirVElisa.setVisibility(View.VISIBLE);
                if(estado == true) subirVElisa.setEnabled(true);
                else subirVElisa.setEnabled(false);
                break;
            //tablet B
            case "B":
                verB.setVisibility(View.GONE);
                subirB.setVisibility(View.VISIBLE);
                if(estado == true) subirB.setEnabled(true);
                else subirB.setEnabled(false);
                break;
            case "C":
                verC.setVisibility(View.GONE);
                subirC.setVisibility(View.VISIBLE);
                if(estado == true) subirC.setEnabled(true);
                else subirC.setEnabled(false);
                break;
            case "David":
                verDavid.setVisibility(View.GONE);
                subirDavid.setVisibility(View.VISIBLE);
                if(estado == true) subirDavid.setEnabled(true);
                else subirDavid.setEnabled(false);
                break;
            case "Asentamiento olmos":
                verOlmos.setVisibility(View.GONE);
                subirOlmos.setVisibility(View.VISIBLE);
                if(estado == true) subirOlmos.setEnabled(true);
                else subirOlmos.setEnabled(false);
                break;
            case "Zaldivar":
                verZaldivar.setVisibility(View.GONE);
                subirZaldivar.setVisibility(View.VISIBLE);
                if(estado == true) subirZaldivar.setEnabled(true);
                else subirZaldivar.setEnabled(false);
                break;
            default:
                break;
        }
    }

    public boolean SubirDatos(String sectorSub){
        boolean resultado = false;
        String peridoActual = GetPeriodoActual();
        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "DATOS", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getReadableDatabase();
        if (db != null) {
            String aux = "Select Conexiones_Numero, Conexiones_Actual FROM " + "conexiones" + " WHERE " + "Conexiones_Periodo" +
                    " = \"" + peridoActual + "\"" + " AND Conexiones_Sector" + " = \"" + sectorSub + "\"" + " AND Conexiones_Medido"+
                    " = \"" + 1 + "\"";
            try {
                Cursor c = db.rawQuery(aux, null);
                if (c != null) {
                    if (c.moveToFirst()) {
                        do{
                            int idLeido = c.getInt(0);
                            int actulLeido = c.getInt(1);
                            SubirADBServer(idLeido, actulLeido);
                        }while (c.moveToNext());
                        resultado = true;
                    } else {
                        Toast.makeText(MenuPrincipal.this, "Error al leer los datos", Toast.LENGTH_SHORT).show();
                        resultado = false;
                    }
                }
                db.close();
            } catch (Exception e) {
                Toast.makeText(MenuPrincipal.this, "Error en la base de datos", Toast.LENGTH_SHORT).show();
                resultado = false;
            }
        } else {
            Toast.makeText(this, "No se puede accceder a la base de datos", Toast.LENGTH_SHORT).show();
        }

        return  resultado;
    }

    public void SubirADBServer(int idConexionDB, int actualDB){
        pedidoSubir = false;
        String anio = GetAño();
        String mes = GetMes();
        //String mes = "1";

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://" + ip + "/" + "SubirDatos.php?" + "nConexion=" + idConexionDB + "&mes=" + mes +
                "&anio=" + anio + "&actual=" + actualDB;
        client.post(url, null, new AsyncHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(pedidoSubir == false){
                    pedidoSubir = true;
                    if (statusCode == 200){
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(new String(responseBody));
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(MenuPrincipal.this, "Datos subidos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(pedidoSubir == false){
                    if(pedidoSubir == false){
                        pedidoSubir = true;
                        String errore = "Error: " + statusCode;
                        Toast.makeText(MenuPrincipal.this, errore, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if (pedidoSubir == false){
                    Toast.makeText(MenuPrincipal.this, "Tardo mucho", Toast.LENGTH_SHORT).show();
                    pedidoSubir = true;
                }
            }
        }, 3000);
    }

    public String GetMes(){
        return new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
    }
    public String GetAño(){
        return new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
    }

    public void SectorSubido(String sectorSubido){
        String periodo = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());

        ConexionesDBHelper conexionesDBHelper = new ConexionesDBHelper(this, "SUBIDO", null, 1);
        SQLiteDatabase db = conexionesDBHelper.getWritableDatabase();
        if(db != null){
            ContentValues registronuevo = new ContentValues();
            registronuevo.put("Subidos_Sector", sectorSubido);
            registronuevo.put("Subidos_Periodo", periodo);
            long i = db.insert("subidos", null, registronuevo);
            if(i>0){
                Toast.makeText(this, "Sector subido", Toast.LENGTH_SHORT).show();
                MostrarSubir(sectorSubido);
            }
            db.close();
        }
    }
}
