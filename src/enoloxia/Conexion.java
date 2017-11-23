
package enoloxia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {
    BufferedReader br;
    Connection conn;
    PreparedStatement ps;
    PreparedStatement ps1;
    PreparedStatement ps2;
   
    Statement st;
    ResultSet rs;
    String ruta = "/home/oracle/Downloads/analisis.txt";
    String [] contenido;

    
     public void conexion(){
        
            String driver = "jdbc:oracle:thin:";
            String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
            String porto = "1521";
            String sid = "orcl";
            String usuario = "hr";
            String password = "hr";
            String  url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;
        try {
            //para conectar co native protocal all java driver: creamos un obxecto Connection usando o metodo getConnection da clase  DriverManager
            conn = DriverManager.getConnection(url);
            System.out.println("Conexión realizada correctamente ");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
     public void lerArquivo(){
        try {
            br = new BufferedReader(new FileReader(ruta));
            
            while(br.ready()){
                
            contenido = br.readLine().split(",");
             
            insertar(contenido[0],contenido[4],contenido[5],contenido[1],contenido[6]);
            
                
            
            }
          
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao leelo arquivo "+ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
     }
     
     public void insertar(String codigo,String tratacidez,String ac,String acidez,String dni){
         
         String select1 = "select nomeu,acidezmin,acidezmax from uvas where tipo ='"+tratacidez+"'";
         String select2 = "UPDATE clientes set numerodeanalisis =numerodeanalisis+? where dni =?";
        try {
         
            ps1 = conn.prepareStatement(select1);
            ps2 = conn.prepareStatement(select2);
            rs = ps1.executeQuery();
            rs.next();     
            ps2.setInt(1,1);
            ps2.setString(2, dni);
            ps2.execute();
            ps = conn.prepareStatement("insert into xerado(num,nomeuva,tratacidez,total) values(?,?,?,?)");
            ps.setString(1,codigo);
            ps.setString(2,rs.getString("nomeu"));
            if(Integer.parseInt(acidez)<Integer.parseInt(rs.getString("acidezmin"))){
                ps.setString(3,"Subir acidez");
            } else if(Integer.parseInt(acidez)>Integer.parseInt(rs.getString("acidezmax"))){
                ps.setString(3, "Bajar acidez");
        }else{
                ps.setString(3, "Equilibrada");
            }
            ps.setInt(4,Integer.parseInt(ac)*15);
            ps.execute();
            conn.commit();
            
        } catch (SQLException ex) {
            System.out.println("Error "+ex.getMessage());
        }
        
     
        
     }
     
     
     public void leerXml(){
         
         
         
     }
     
}
