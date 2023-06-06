/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pruebatecnica;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author alejo
 */
public class CConexion {
    
    Connection cn = null;
    
    String usuario = "root";
    String password = "";
    String bd = "konecta";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(cadena, usuario, password);
            //JOptionPane.showMessageDialog(null, "Conexión satisfactoria");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectarse: "+ e.toString());
        }
        
       return cn;
    }
}


