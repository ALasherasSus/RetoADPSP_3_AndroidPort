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
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuBD extends AppCompatActivity {

    String user;
    String password;
    String puerto;
    String servidor;
    String datos;
    String currentPhotoPath;
    byte[] byteArray;
    Uri fotoUri;
    Button subir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bd);

        Bundle bundle = getIntent().getExtras();
        user=bundle.getString("usuario");
        password=bundle.getString("password");
        servidor=bundle.getString("servidor");
        puerto=bundle.getString("puerto");
        datos=bundle.getString("datos");

        subir = (Button)findViewById(R.id.btnSubirFoto);
        subir.setEnabled(false);
        (findViewById(R.id.btnCamara)).setOnClickListener(V -> {

            int permissionCheck = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
            } else {
                abrirCamara();
            }

        });
    }

    public void Consulta(View view){

        Intent i = new Intent(this, ConsultaBD.class );
        i.putExtra("servidor", servidor);
        i.putExtra("puerto", puerto);
        i.putExtra("usuario", user);
        i.putExtra("password", password);
        i.putExtra("datos", datos);
        startActivity(i);
    }

    public void SubirFoto(View view){
        Intent i = new Intent(this, SubirFotoBD.class );
        i.putExtra("servidor", servidor);
        i.putExtra("puerto", puerto);
        i.putExtra("usuario", user);
        i.putExtra("password", password);
        i.putExtra("datos", datos);
        i.putExtra("url",currentPhotoPath );
        i.putExtra("uri",fotoUri );
        try {
            startActivity(i);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void abrirCamara() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // if(intent.resolveActivity(getPackageManager()) !=null){

        File imagenArchivo=null;

        try {
            imagenArchivo=createImageFile();
        }catch(Exception e){
            Log.e("Error",e.toString());
        }
        if(imagenArchivo !=null){
            fotoUri = FileProvider.getUriForFile(this,"com.cdp.camara.fileprovider",imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
            startActivityForResult(intent,1);
        }
        startActivityForResult(intent,1);
        subir.setEnabled(true);
        // }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 &&resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap= BitmapFactory.decodeFile(currentPhotoPath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;

    }


    public void Filtro(View view){
        Intent i = new Intent(this, Municipio.class );
        i.putExtra("servidor", servidor);
        i.putExtra("puerto", puerto);
        i.putExtra("usuario", user);
        i.putExtra("password", password);
        i.putExtra("datos", datos);
        i.putExtra("url",currentPhotoPath );
        i.putExtra("uri",fotoUri );
        try {
            startActivity(i);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Top(View view){
        Intent i =new Intent(this,mostrarTop.class);
        i.putExtra("servidor", servidor);
        i.putExtra("puerto", puerto);
        i.putExtra("usuario", user);
        i.putExtra("password", password);
        i.putExtra("datos", datos);

        try {
            startActivity(i);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}