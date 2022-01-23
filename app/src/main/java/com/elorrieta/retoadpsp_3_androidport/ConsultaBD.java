package com.elorrieta.retoadpsp_3_androidport;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import java.util.ArrayList;

public class ConsultaBD extends Activity {

    private TextView txtBaseDatos,txtPuerto,txtServidor,txtUsuario,txtPass;
    private EditText edConsulta, edResultado;
    private Bundle bundle;
    private String[] datosConexion = null;
    String Tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_bd);


        txtServidor = (TextView)findViewById(R.id.txtServidor);
        txtPuerto = (TextView)findViewById(R.id.txtPuerto);
        txtUsuario = (TextView)findViewById(R.id.txtUsuario);
        txtPass = (TextView)findViewById(R.id.edPasswordCon);
        txtBaseDatos = (TextView)findViewById(R.id.txtBaseDatos);

        edConsulta = (EditText)findViewById(R.id.edConsulta);
        edResultado = (EditText)findViewById(R.id.edResultado);

        edResultado.setKeyListener(null);

        bundle = getIntent().getExtras();
        //Obtenemos los valores introducidos en la Activity principal
        txtServidor.setText(bundle.getString("servidor"));
        txtPuerto.setText(bundle.getString("puerto"));
        txtUsuario.setText(bundle.getString("usuario"));
        txtPass.setText(bundle.getString("password"));
        txtBaseDatos.setText(bundle.getString("datos"));

        Tipo="CONSULTA";
    }

    public void consultaSelect(View view)
    {
        edConsulta.setText("Select * from diario");
    }



    //Evento On Click que conecta con el servidor MySQL y procesa las consultas mostrando los resultados
    public void mostrarResultados(View view)
    {
        ArrayList<String> NombreP = new ArrayList<>();
        String consulta = edConsulta.getText().toString();
        String[] resultadoSQL = null;
        try{
            if(consulta.equals(""))
            {
                Toast.makeText(this, "Debe indicar una consulta Transact-SQL válida.", Toast.LENGTH_LONG).show();
            }else
            {
                datosConexion = new String[]{
                        txtServidor.getText().toString(),
                        txtPuerto.getText().toString(),
                        txtBaseDatos.getText().toString(),
                        txtUsuario.getText().toString(),
                        txtPass.getText().toString(),
                        consulta,
                        Tipo
                };
                //Asignamos el driver a una variable de tipo String
                String driver = "com.mysql.jdbc.Driver";
                //Cargamos el driver del conector JDBC
                Class.forName(driver).newInstance ();
                resultadoSQL = new ConsultasAsincrona().execute(datosConexion).get();
                Toast.makeText(ConsultaBD.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

                String resultadoConsulta = resultadoSQL[0];
                String numFilas = resultadoSQL[1];
                String numColumnas = resultadoSQL[2];
                edResultado.setText(resultadoConsulta + "Número de filas devueltas: " +
                        numFilas + "\nNúmero de columnas devueltas: " + numColumnas);
            }
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}