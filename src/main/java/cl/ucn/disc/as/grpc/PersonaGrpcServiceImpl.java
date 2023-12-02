/*
 * Copyright (c) 2023. Arquitectura de Software, DISC, UCN.
 */

package cl.ucn.disc.as.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * The gRPC service class.
 *
 * @author Arquitectura de Software.
 */
@Slf4j
public final class PersonaGrpcServiceImpl extends PersonaGrpcServiceGrpc.PersonaGrpcServiceImplBase {

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void retrieve(PersonaGrpcRequest request, StreamObserver<PersonaGrpcResponse> responseObserver) {

        log.debug("Retrieving PersonaGrpc with rut={}.", request.getRut());

        PersonaGrpc personaGrpc = PersonaGrpc.newBuilder()
                .setRut("231231232")
                .setNombre("Allan")
                .setApellidos("Cortes Cortes")
                .setEmail("allan.cortes@alumnos.ucn.cl")
                .setTelefono("+569 582112332")
                .build();

        responseObserver.onNext(PersonaGrpcResponse.newBuilder().setPersona(personaGrpc).build());

        responseObserver.onCompleted();
    }
}