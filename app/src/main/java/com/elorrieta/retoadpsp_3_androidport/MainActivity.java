package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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
        YoYo.with(Techniques.FadeIn)
                .duration(900)
                .repeat(1)
                .playOn(findViewById(R.id.etUsuario));
        YoYo.with(Techniques.FadeIn)
                .duration(900)
                .repeat(1)
                .playOn(findViewById(R.id.etContra));
        YoYo.with(Techniques.FadeIn)
                .duration(900)
                .repeat(1)
                .playOn(findViewById(R.id.button));
        YoYo.with(Techniques.FadeIn)
                .duration(900)
                .repeat(1)
                .playOn(findViewById(R.id.button2));
        YoYo.with(Techniques.FadeIn)
                .duration(900)
                .repeat(1)
                .playOn(findViewById(R.id.tvUsuario));
        YoYo.with(Techniques.FadeIn)
                .duration(900)
                .repeat(1)
                .playOn(findViewById(R.id.tvContra));

        etUsuario=(EditText)findViewById(R.id.etUsuario);
        etContra=(EditText)findViewById(R.id.etContra);
    }

    public void login(View v){
        //PROVISIONAL: DEBE CAMBIARSE AL CONECTAR LA APLICACIÓN A LA BASE DE DATOS
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
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(1)
                    .playOn(findViewById(R.id.etUsuario));
        }else if(!pass2.equals(password)){
            cadena=getString(R.string.sesionFracasoContra);
            Toast notificacion= Toast.makeText(this,cadena,Toast.LENGTH_LONG);
            notificacion.show();
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(1)
                    .playOn(findViewById(R.id.etContra));
        }else{
            //cadena=getString(R.string.sesionExito);
            etContra.setText("");
            Intent i = new Intent(this, MenuPrincipal.class );
            startActivity(i);
        }

    }

    public void registrarse(View v){
        Intent i = new Intent(this, Registro.class );
        startActivity(i);
    }
}