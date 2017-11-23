
package enoloxia;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Enoloxia {

   
    public static void main(String[] args) {
      Conexion conect = new Conexion();
      conect.conexion();
      conect.lerArquivo();
   
    
}
}