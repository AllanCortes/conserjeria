/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import cl.ucn.disc.as.model.exceptions.IllegalDomainException;
import cl.ucn.disc.as.utils.ValidationUtils;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * The Persona class.
 *
 * @author Allan Cortes Cortes.
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Persona extends BaseModel {

    /**
     * The RUT.
     */
    @NotNull
    private String rut;

    /**
     * The Nombre.
     */
    @NotNull
    private String nombre;

    /**
     * The Apellidos.
     */
    @NotNull
    private String apellidos;

    /**
     * The Email.
     */
    @NotNull
    private String email;

    /**
     * The Telefono.
     */
    @NotNull
    private String telefono;

    /**
    *Custom builder to validate
     */
    public static class PersonaBuilder{

        /**
        * @return The Persona
         */
        public Persona build(){
            //validate the rut
            if(!ValidationUtils.isRutValid(this.rut)){
             throw new IllegalDomainException("RÚT no valido : " + this.rut);
            }
            //validate the email
            if(!ValidationUtils.isEmailValid(this.email)){
                throw new IllegalDomainException("Email no valido : "+ this.email);
            }

            //TODO: Agregar resto de valdiaciones

            return new Persona(
                    this.rut,
                    this.nombre,
                    this.apellidos,
                    this.email,
                    this.telefono
            );
        }
    }

}
