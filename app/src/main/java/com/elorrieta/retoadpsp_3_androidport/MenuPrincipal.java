package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void basedeDatos(View v){
            Intent i = new Intent(this, ConexionBBDD.class );
            i.putExtra("Usuario", usuario);
            i.putExtra("Contraseña", contraseña);
            startActivity(i);
        }


}