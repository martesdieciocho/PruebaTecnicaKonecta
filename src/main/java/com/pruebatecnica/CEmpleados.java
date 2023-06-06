
package com.pruebatecnica;

import com.mysql.cj.xdevapi.Result;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CEmpleados {
    
    //Declaración de variables.
    String fecha_ingreso;
    String nombre;
    int Salario;

    //Declaración de variables.
    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSalario() {
        return Salario;
    }

    public void setSalario(int Salario) {
        this.Salario = Salario;
    }
    
    //Método para insertar en base de datos
    public void insertarEmpleado(JTextField paramFecha, JTextField paramNombre, JTextField paramSalario){
            
        //obteniendo entradas
        setFecha_ingreso(paramFecha.getText());
        setNombre(paramNombre.getText());
        setSalario(Integer.parseInt(paramSalario.getText()));
        
        //Instancia de conexión
        CConexion cn = new CConexion();
        
        //Consulta a realizar para la base de datos
        String query = "insert into empleado (fecha_ingreso, nombre, salario) values (?, ?, ?);";
        
        try {
            //Inserción de datos
            CallableStatement cs = cn.estableceConexion().prepareCall(query);
            cs.setString(1, getFecha_ingreso());
            cs.setString(2, getNombre());
            cs.setInt(3, getSalario());
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Insertado correctamente: ");
                    
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Error al insertar: "+ e.toString());
        }
    }
    
    //Método para listar los datos
    public void mostrarEmpleados(JTable paramTotalEmpleados){
        //Instancia de conexión
        CConexion cn = new CConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        
        //Consulta a realizar para la base de datos
        String query = "Select * from empleado;";
        
        //Inserción de columans
        modelo.addColumn("id");
        modelo.addColumn("fecha_ingreso");
        modelo.addColumn("nombre");
        modelo.addColumn("salario");
        
        paramTotalEmpleados.setModel(modelo);
        
        String[] datos = new String[4];
        Statement st;
        
        try {
            st = cn.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                datos[0]= rs.getString(1);
                datos[1]= rs.getString(2);
                datos[2]= rs.getString(3);
                datos[3]= rs.getString(4);
                
                //insertando los datos
                modelo.addRow(datos);
            }
            
            paramTotalEmpleados.setModel(modelo);
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Error al insertar: "+ e.toString());
        }
        
    }
    
}
