/** .
 * Copyright (c) 2023. Arquitectura de Software, DISC, UCN
 */
package cl.ucn.disc.as.ui;

import cl.ucn.disc.as.grpc.PersonaGrpc;
import cl.ucn.disc.as.grpc.PersonaGrpcRequest;
import cl.ucn.disc.as.grpc.PersonaGrpcResponse;
import cl.ucn.disc.as.grpc.PersonaGrpcServiceGrpc;
import cl.ucn.disc.as.model.Persona;

import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import java.util.Optional;
public class WebController  implements RoutesConfigurator {

    /*
     * The Sistema.
     */

    private final Sistema sistema;

    /*
     * The web controller.
     */
    public WebController(){
        this.sistema = new SistemaImpl(DB.getDefault());
        // FIXME: poputlate in case of new database.
        this.sistema.populate();
    }
    /**
     * Configure the routes
     * @param app to use
     */
    @Override
    public void configure(final Javalin app){
        app.get("/", ctx -> {
            ctx.result("Welcome to Conserjeria API REST");
        });

        //the personas api
        app.get("api/personas", ctx -> {
            ctx.json(this.sistema.getPersonas());
        });
        app.get("/personas/rut/{rut}", ctx -> {
            String rut = ctx.pathParam("rut");
            Optional<Persona> oPersona = Optional.ofNullable(this.sistema.getPersona(rut));
            ctx.json(oPersona.orElseThrow(() -> new NotFoundResponse("Can't find Persona with rut: " + rut)));
        });

        app.get("/api/grpc/personas/rut/{rut}", ctx -> {

            String rut = ctx.pathParam("rut");

            // the channel
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress("127.0.0.1", 50123)
                    .usePlaintext()
                    .build();

            // stub
            PersonaGrpcServiceGrpc.PersonaGrpcServiceBlockingStub stub =
                    PersonaGrpcServiceGrpc.newBlockingStub(channel);

            // call the gprc
            PersonaGrpcResponse response = stub.retrieve(PersonaGrpcRequest
                    .newBuilder()
                    .setRut("203471564")
                    .build());

            // get the response
            PersonaGrpc personaGrpc = response.getPersona();

            // return the persona
            Optional<Persona> oPersona = Optional.of(Persona.builder()
                    .rut(personaGrpc.getRut())
                    .nombre(personaGrpc.getNombre())
                    .apellidos(personaGrpc.getApellidos())
                    .email(personaGrpc.getEmail())
                    .build());

            ctx.json(oPersona.orElseThrow(() -> new NotFoundResponse("Can't find Persona with rut: " + rut)));

        });
    }

}
