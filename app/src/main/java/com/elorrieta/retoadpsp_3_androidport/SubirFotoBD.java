package com.elorrieta.retoadpsp_3_androidport;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class SubirFotoBD extends AppCompatActivity {


    private Button btnSubir;
    private ImageView imageView;
    Bitmap bitmap;
    private String[] datosConexion = null;

    String servidor;
    String puerto;
    String usuario;
    String password;
    String bd;
    String currentPhotoPath;
    byte[] byteArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto_bd);

        btnSubir = (Button) findViewById(R.id.btnSubir);
        imageView  = (ImageView) findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();
        servidor = bundle.getString("servidor");
        puerto=(bundle.getString("puerto"));
        usuario=(bundle.getString("usuario"));
        password=(bundle.getString("password"));
        bd=(bundle.getString("datos"));
        currentPhotoPath=(bundle.getString("url"));
        Uri uri=(bundle.getParcelable("uri"));

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);





    }

    public void mostrarResultados(View view) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        /*Bitmap imgBitmap= BitmapFactory.decodeFile(currentPhotoPath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();*/

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        String myl = Base64.encodeToString(bytes, Base64.DEFAULT);



        String consulta = "INSERT INTO informacion(idUsuario,foto) values ("+1+","+myl+")";
        String[] resultadoSQL = null;
        try{

                datosConexion = new String[]{
                        servidor,
                        puerto,
                        bd,
                        usuario,
                        password,
                        consulta};

                //Asignamos el driver a una variable de tipo String
                String driver = "com.mysql.jdbc.Driver";
                //Cargamos el driver del conector JDBC
                Class.forName(driver).newInstance ();
                resultadoSQL = new CargarFotoAsincrona().execute(datosConexion).get();
                Toast.makeText(SubirFotoBD.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }catch(Exception ex) {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }








}

