package com.elorrieta.retoadpsp_3_androidport;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CargarFotoAsincrona extends AsyncTask<String[],Void,String[]>
{
    private Connection conexionMySQL;
    private PreparedStatement st = null;


    @SuppressLint("LongLogTag")
    @Override
    protected String[] doInBackground(String[]... datos)
    {
        String sql = datos[0][5];
        String resultadoSQL = "";
        String[] totalResultadoSQL = null;
        int numColumnas;
        int numFilas;
        String SERVIDOR = datos[0][0];
        String PUERTO = datos[0][1];
        String BD = datos[0][2];
        String USUARIO = datos[0][3];
        String PASSWORD = datos[0][4];

        try{
            /*Establecemos la conexión con el Servidor MySQL indicándole la cadena de conexión formada por la dirección ip,
            puerto del servidor, la base de datos a la que vamos a conectarnos, y el usuario y contraseña de acceso al servidor.*/
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BD,
                    USUARIO,PASSWORD);

            st = conexionMySQL.prepareStatement(sql);
            //Se ejecutará la consulta indicada en el campo edConsulta
            st.executeUpdate();
            //Posicionamos el cursor en la última posición para saber el total de filas devueltas

        }catch(SQLException ex)
        {
            Log.d("No ha sido posible conectar con la base de datos", ex.getMessage());
        }
        finally
        {
            try
            {

                st.close();
                conexionMySQL.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return totalResultadoSQL;
    }
}

