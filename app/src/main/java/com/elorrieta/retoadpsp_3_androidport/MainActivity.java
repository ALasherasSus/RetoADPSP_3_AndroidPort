package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etContra;
    private SharedPreferences p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        String usu = p.getString("usuario","");
        String pass = p.getString("password","");
        if (usu == "" && pass == "") {
            editor.putString("usuario", "arki");
            editor.putString("password", "darki");
            editor.apply();
        }

        etUsuario=(EditText)findViewById(R.id.etUsuario);
        etContra=(EditText)findViewById(R.id.etContra);
    }

    public void login(View v){

        String usuario=etUsuario.getText().toString();
        String password=etContra.getText().toString();
        String cadena="";

        p = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        String usu2 = p.getString("usuario","");
        String pass2 = p.getString("password", "");

        if(!usu2.equals(usuario)){
            cadena=getString(R.string.sesionFracasoUsuario);
            Toast notificacion= Toast.makeText(this,cadena,Toast.LENGTH_LONG);
            notificacion.show();
        }else if(!pass2.equals(password)){
            cadena=getString(R.string.sesionFracasoContra);
            Toast notificacion= Toast.makeText(this,cadena,Toast.LENGTH_LONG);
            notificacion.show();
        }else{
            //cadena=getString(R.string.sesionExito);
            etContra.setText("");
            Intent i = new Intent(this, MenuPrincipal.class );
            startActivity(i);
        }

    }

    public void registrarse(View v){

        String usuario=etUsuario.getText().toString();
        String password=etContra.getText().toString();
        String cadena="";

        p = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        String usu2 = p.getString("usuario","");
        String pass2 = p.getString("password", "");

        if(!usu2.equals(usuario)){
            cadena=getString(R.string.sesionFracasoUsuario);
            Toast notificacion= Toast.makeText(this,cadena,Toast.LENGTH_LONG);
            notificacion.show();
        }else if(!pass2.equals(password)){
            cadena=getString(R.string.sesionFracasoContra);
            Toast notificacion= Toast.makeText(this,cadena,Toast.LENGTH_LONG);
            notificacion.show();
        }else{
            //cadena=getString(R.string.sesionExito);
            etContra.setText("");
            Intent i = new Intent(this, MenuPrincipal.class );
            startActivity(i);
        }

    }
}