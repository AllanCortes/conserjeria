package cl.ucn.disc.as;

import cl.ucn.disc.as.grpc.PersonaGrpcServiceImpl;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import cl.ucn.disc.as.ui.ApiRestServer;
import cl.ucn.disc.as.ui.WebController;
import io.ebean.DB;
import io.ebean.Database;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * The main.
 * @author Allan Cortes Cortes
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        log.debug( "Starting Main ..");
        //Obtener la BD
        Database db = DB.getDefault();
        //Inicializar el sistema
        Sistema sistema = new SistemaImpl(db);
        //sistema.populate();

        log.debug("Library path : {} ", System.getProperty("java.library.path"));
        // Start the API Rest Server
        log.debug("Starting ApiRest server ..");
        Javalin app = ApiRestServer.start(7070, new WebController());

        // stop the API REST server.
        // app.stop();

        // Start the gRPC server
        log.debug("Starting the gRPC server ..");

        Server server = ServerBuilder
                .forPort(50123)
                .addService(new PersonaGrpcServiceImpl())
                .build();

        server.start();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        // wait for the stop
        server.awaitTermination();

        log.debug("Done. :)");


    }
}
