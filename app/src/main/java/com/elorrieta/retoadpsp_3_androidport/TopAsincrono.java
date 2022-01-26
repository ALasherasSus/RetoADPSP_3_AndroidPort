package com.elorrieta.retoadpsp_3_androidport;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TopAsincrono extends AsyncTask<String[],Void,String[]>
{
    private Connection conexionMySQL;
    private Statement st = null;
    private ResultSet rs = null;




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

            st = conexionMySQL.createStatement();
            //Se ejecutará la consulta indicada en el campo edConsulta
            rs = st.executeQuery(sql);
            //Posicionamos el cursor en la última posición para saber el total de filas devueltas
            rs.last();
            //Se comprueba que el número de filas devueltas sea igual a 0.
            if(rs.getRow() == 0)
            {
                String mensajeError = "No se ha producido ningún resultado. Revise la consulta realizada.\n";
                totalResultadoSQL = new String[]{mensajeError, "0", "0"};
            }else
            {
                rs.beforeFirst();
                //Búcle encargado de recorrer y mostrar los resultados a partir de la consulta ejecutada


                while (rs.next()) {
                    resultadoSQL += "Nombre: " + rs.getString(1) + "   Nº Estaciones: " + rs.getString(2)+"\n"
                    +"*****************************\n";
                }

                //Variable que almacenará el número de columnas obtenidas de la consulta Transact-SQL
                numColumnas = rs.getMetaData().getColumnCount();
                //Posicionamos el cursor en la última posición para posteriormente saber el total de filas devueltas
                rs.last();
                //Variable que almacenará el número de filas devueltas por la consulta
                numFilas = rs.getRow();
                totalResultadoSQL = new String[]
                        {
                                resultadoSQL,

                        };
            }

        }catch(SQLException ex)
        {
            Log.d("No ha sido posible conectar con la base de datos", ex.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
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

