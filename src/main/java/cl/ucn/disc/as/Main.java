package cl.ucn.disc.as;

import cl.ucn.disc.as.ui.WebController;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import cl.ucn.disc.as.ui.ApiRestServer;
import cl.ucn.disc.as.services.SistemaImpl;
import cl.ucn.disc.as.services.Sistema;
import io.ebean.Database;
import io.ebean.DB;
/**
 * The main.
 * @author Allan Cortes Cortes
 */
@Slf4j
public class Main {
    public static void main(String[] args){

        log.debug( "Starting Main ..");
        //Obtener la BD
        Database db = DB.getDefault();
        //Inicializar el sistema
        Sistema sistema = new SistemaImpl(db);

        sistema.populate();
        log.debug("Library path : {} ", System.getProperty("java.library.path"));
        // Start the API Rest Server
        Javalin app = ApiRestServer.start(7070, new WebController());


        //log.debug("Stopping...");

        //app.stop();

        log.debug( "Done. :)");


    }
}
