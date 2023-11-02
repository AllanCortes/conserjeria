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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.Instant;
import java.util.Date;

/**
 * The Pago class.
 *
 * @author Allan Cortes Cortes.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Pago extends BaseModel{

    /**
     * The Fecha .
     */
    @NotNull
    private Instant fechaPago;

    /**
     * The Monto.
     */
    @NotNull
    private int monto;

    /**
     * The Contrato Relacion.
     */
    @ManyToOne
    private Contrato contrato;

    /**
     *Custom builder to validate
     */
    public static class PagoBuilder{

        /**
         * @return The Pago
         */
        public Pago build(){

            //TODO: Agregar resto de valdiaciones

            return new Pago(
                    this.fechaPago,
                    this.monto,
                    this.contrato
            );
        }
    }

}
