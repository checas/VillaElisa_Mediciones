package com.example.sergio.villaelisa_mediciones;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.sergio.villaelisa_mediciones.R.*;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    EditText usuario, password;
    boolean pedidoUsuario;
    String ipActual = null, tabletActual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        logo = (ImageView) findViewById(id.Logo);
        usuario = (EditText) findViewById(id.ET_Usuario);
        password = (EditText) findViewById(id.ET_Password);

        LeerConfig();

        /* Bajaba los usuarios de la app desde la DB en pc
        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //ActualizarUsuarios();
                if(ipActual != null) test();
                else Toast.makeText(MainActivity.this, "Dispositivo no configurado", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        */

    }
    @Override
    protected void onResume() {
        super.onResume();
        LeerConfig();
    }
    /*Funcion q permite bajar los usuarios de la DB si la ip esta configurada
    public void test(){
        ActualizarUsuarios();
    }
    */

    public void LeerConfig(){
        ConfiguracionesDBHelper configuracionesDBHelper = new ConfiguracionesDBHelper(this, "CONFIG",null,1);
        SQLiteDatabase db = configuracionesDBHelper.getReadableDatabase();
        if(db != null) {
            String aux = "Select Configuraciones_Ip, Configuraciones_Tablet FROM " + "configuraciones" + " WHERE " + "Configuraciones_Id" + " = \"" + 1 + "\"";
            try{
                Cursor c = db.rawQuery(aux, null);
                if(c != null){
                    if(c.moveToFirst()){
                        MostrarAdvertencia(false);
                        ipActual = c.getString(0);
                        tabletActual = c.getString(1);
                    }else{
                        MostrarAdvertencia(true);
                    }
                }
                //db.close();
            }catch (Exception e){
                MostrarAdvertencia(true);
            }
            db.close();
        }
    }

    public void MostrarAdvertencia(boolean mostrar){
        TextView advertencia = findViewById(id.TV_SinBase);
        if(mostrar == true) advertencia.setVisibility(View.VISIBLE);
        else advertencia.setVisibility(View.GONE);
    }
    /*descarga los usarios desde la DB en pc
    public void ActualizarUsuarios(){
        pedidoUsuario = false;

        AsyncHttpClient client = new AsyncHttpClient();

        //String url = getString(R.string.ip) + "GetUsuarios.php";
        String url = "http://" + ipActual + "/" + "GetUsuarios.php";
        client.post(url, null, new AsyncHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(pedidoUsuario == false){
                    pedidoUsuario = true;
                    if (statusCode == 200){

                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(new String(responseBody));
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                int cantidad = jsonResponse.getInt("cantidad");
                                int id = 0;
                                String usuario = "";
                                String password = "";
                                int borrado = 0;
                                for (int i = 0; i < cantidad; i++) {
                                    String indice = Integer.toString(i);
                                    JSONObject datos = jsonResponse.getJSONObject(indice);
                                    id = datos.getInt("id");
                                    usuario = datos.getString("user");
                                    password = datos.getString("pass");
                                    borrado = datos.getInt("borrado");
                                    InsertarEnDBUsuarios(id, usuario, password, borrado);
                                }
                                Toast.makeText(MainActivity.this, "Usuarios descargados", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "Todo OK", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(pedidoUsuario == false){
                    if(pedidoUsuario == false){
                        pedidoUsuario = true;
                        String errore = "Error: " + statusCode;
                        Toast.makeText(MainActivity.this, errore, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if (pedidoUsuario == false){
                    Toast.makeText(MainActivity.this, "Tardo mucho", Toast.LENGTH_SHORT).show();
                    pedidoUsuario = true;
                }
            }
        }, 3000);
   }
   */
    /* Agraga los usuarios descargados desde la pc a la DB del dispositivo
    public void InsertarEnDBUsuarios(int id, String user, String pass, int borrado){
        UsuariosDBHelper usuariosDBHelper = new UsuariosDBHelper(this, "USER", null, 1);
        SQLiteDatabase db = usuariosDBHelper.getWritableDatabase();
        if(db != null){
            ContentValues registronuevo = new ContentValues();
            registronuevo.put("Usuarios_Id", id);
            registronuevo.put("Usuarios_User", user);
            registronuevo.put("Usuarios_Pass", pass);
            registronuevo.put("Usuarios_Borrado", borrado);
            long i = db.insert("usuarios", null, registronuevo);
            if(i>0){
                Toast.makeText(this, "Usuarios actualizados", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }
    */
    public void Logear(View view){

        String user = usuario.getText().toString();
        String pass = password.getText().toString();
        if(user.length() == 0 || pass.length() == 0){
            Toast.makeText(this, "Los campos no pueden ser vacios", Toast.LENGTH_SHORT).show();
        }else{
            switch (user){
                case "admin":
                    if(pass.equals("admin")){
                        Intent intent = new Intent(MainActivity.this, AdminLogin.class);
                        MainActivity.this.startActivity(intent);
                    }else{
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "claudio":
                    if(tabletActual != null){
                        if(pass.equals("claudio")){
                            int idLogeo = 1;
                            String userLogeo = "Claudio";
                            Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                            intent.putExtra("id_logeo", idLogeo);
                            intent.putExtra("user_logeo", userLogeo);
                            intent.putExtra("ipActual", ipActual);
                            intent.putExtra("tabletActual",tabletActual);
                            MainActivity.this.startActivity(intent);
                        }else{
                            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else Toast.makeText(this, "Tablet no configurada", Toast.LENGTH_SHORT).show();
                    break;
                case "david":
                    if(tabletActual != null){
                        if(pass.equals("david")){
                            int idLogeo = 2;
                            String userLogeo = "David";
                            Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                            intent.putExtra("id_logeo", idLogeo);
                            intent.putExtra("user_logeo", userLogeo);
                            intent.putExtra("ipActual", ipActual);
                            intent.putExtra("tabletActual",tabletActual);
                            MainActivity.this.startActivity(intent);
                        }else{
                            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else Toast.makeText(this, "Tablet no configurada", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                    break;
            }

            /*
            if(user.equals("admin") && pass.equals("admin")){
                Intent intent = new Intent(MainActivity.this, AdminLogin.class);
                MainActivity.this.startActivity(intent);
            }else{
                UsuariosDBHelper usuariosDBHelper = new UsuariosDBHelper(this, "USER",null,1);
                SQLiteDatabase db = usuariosDBHelper.getReadableDatabase();
                if(db != null) {
                    String aux = "Select Usuarios_Id, Usuarios_User FROM " + "usuarios" + " WHERE " + "Usuarios_User" + " = \"" + user + "\"" +
                            " AND Usuarios_Pass" + " = \"" + pass + "\"" + " AND Usuarios_Borrado" + " = \"" + 0 + "\"";
                    Cursor c = db.rawQuery(aux, null);
                    if(c != null){
                        if(c.moveToFirst()){
                            int idLogeo = c.getInt(0);
                            String userLogeo = c.getString(1);
                            Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                            intent.putExtra("id_logeo", idLogeo);
                            intent.putExtra("user_logeo", userLogeo);
                            intent.putExtra("ipActual", ipActual);
                            intent.putExtra("tabletActual",tabletActual);
                            MainActivity.this.startActivity(intent);
                        }else{
                            Toast.makeText(this, "Error de logeo", Toast.LENGTH_SHORT).show();
                        }
                    }
                    db.close();
                }else{
                    Toast.makeText(this, "No se puede accceder a la base de datos", Toast.LENGTH_SHORT).show();
                }
            }
            */
        }
    }
}
