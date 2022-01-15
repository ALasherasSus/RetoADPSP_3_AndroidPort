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

import com.elorrieta.retoadpsp_3_androidport.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuPrincipal extends AppCompatActivity {
    String usuario;
    String contraseña;
    String currentPhotoPath;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_principal);
        Bundle bundle = getIntent().getExtras();
        usuario=bundle.getString("Usuario");
        contraseña=bundle.getString("Contraseña");
        imageView=(ImageView)findViewById(R.id.imageView);

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
                Uri fotoUri = FileProvider.getUriForFile(this,"com.cdp.camara.fileprovider",imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                startActivityForResult(intent,1);
            }
            startActivityForResult(intent,1);

       // }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 &&resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap= BitmapFactory.decodeFile(currentPhotoPath);
            imageView.setImageBitmap(imgBitmap);
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