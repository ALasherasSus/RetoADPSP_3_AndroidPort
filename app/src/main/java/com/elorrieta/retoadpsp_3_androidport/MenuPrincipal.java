package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {
    String usuario;
    String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Bundle bundle = getIntent().getExtras();
        usuario=bundle.getString("Usuario");
        contraseña=bundle.getString("Contraseña");
    }

    public void dondeEncontrarnos(View v){
        Intent i = new Intent(this, Elorrieta.class );
        startActivity(i);
    }

    public void tuPosicion(View v){
        Intent i = new Intent(this, PosicionActual.class );
        startActivity(i);
    }
    public void camara(View v){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        }
    }
    public void basedeDatos(View v){
            Intent i = new Intent(this, ConexionBBDD.class );
            i.putExtra("Usuario", usuario);
            i.putExtra("Contraseña", contraseña);
            startActivity(i);
        }

}