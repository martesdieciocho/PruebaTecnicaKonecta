
package com.pruebatecnica;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class CSolicitudes {
    
    //Declaración de variables.
    String codigo;
    String descripcion;
    String resumen;
    int id_empleado;

    //Declaración de variables.
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }
    
    //Método para insertar en base de datos
    public void insertarSolicitud(JTextField paramCodigo, JTextField paramDescripcion, JTextField paramResumen, JTextField paramIdEmpleado){
        
        //obteniendo entradas
        setCodigo(paramCodigo.getText());
        setDescripcion(paramDescripcion.getText());
        setResumen(paramResumen.getText());
        setId_empleado(Integer.parseInt(paramIdEmpleado.getText()));
        
        //Instancia de conexión
        CConexion cn = new CConexion();
        
        //Consulta a realizar para la base de datos
        String query ="insert into solicitud (codigo, descripcion, resumen, id_empleado) values (?,?,?,?);";
    
        try {
            //Inserción de datos
            CallableStatement cs = cn.estableceConexion().prepareCall(query);
            cs.setString(1, getCodigo());
            cs.setString(2, getDescripcion());
            cs.setString(3, getResumen());
            cs.setInt(4, getId_empleado());
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Insertado correctamente: ");
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Error al insertar: "+ e.toString());
        }
    }
    
    //Método para listar los datos
    public void mostrarSolicitudes (JTable paramTotalSolicitudes){
        //instancia de conexión
        CConexion cn = new CConexion();
        DefaultTableModel modelo = new DefaultTableModel();
    
        //Consulta a realizar para la base de datos
        String query = "Select * from solicitud INNER JOIN empleado ON solicitud.id_empleado = empleado.id;";
        
        //Inserción de columans
        modelo.addColumn("id");
        modelo.addColumn("Código");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Resumen");
        modelo.addColumn("Nombre empleado");
        
        paramTotalSolicitudes.setModel(modelo);
        
        String[] datos = new String[5];
        Statement st;
        
        try {
            st = cn.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                
                //Recuperando el id del empleado
                String idEmpleado = rs.getString(5);
                // Método para obtener el nombre del empleado
                String nombreEmpleado = obtenerNombreEmpleado(idEmpleado); 
                
                datos[4] = nombreEmpleado;
                
                //insertando los datos
                modelo.addRow(datos);
            }
            paramTotalSolicitudes.setModel(modelo);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: "+ e.toString());
        }
        
    }
    
    // Método para obtener el nombre del empleado
    private String obtenerNombreEmpleado(String idEmpleado) {
    //Instancia de conexión
    CConexion cn = new CConexion();
    String nombreEmpleado = "";

    //Consulta a realizar para la base de datos
    String query = "SELECT nombre FROM empleado WHERE id = " + idEmpleado;

    try {
        Statement st = cn.estableceConexion().createStatement();
        //Ejecución de consulta para la búsqueda del id
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            nombreEmpleado = rs.getString("nombre");
        }

        rs.close();
        st.close(); 
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al obtener nombre del empleado: " + e.getMessage());
    }

    return nombreEmpleado;
}
    
}
