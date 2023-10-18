/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import java.time.Instant;
import java.util.List;
import java.util.Date;

/**
 * The Contrato class.
 *
 * @author Allan Cortes Cortes.
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Contrato extends BaseModel {
    /**
     *  The Fecha Pago .
     */
    @NotNull
    private Instant fechaPago;

    /**
     * The Relación Propietario-contrato.
     */
    @NotNull
    private Persona propietario; // Dueño del contrato

    /**
     * The Relación Departamento-contrato.
     */
    @NotNull
    private Departamento departamento; // Departamento asociado al contrato

    /**
     * The Relación Contrato-Pagos.
     */
    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL) // Relación One-to-Many con Pago
    private List<Pago> pagos; // Lista de pagos relacionados con el contrato

    /**
     *Custom builder to validate
     */
    public static class ContratoBuilder{

        /**
         * @return The Contrato
         */
        public Contrato build(){

            //TODO: Agregar resto de valdiaciones

            return new Contrato(
                    this.fechaPago,
                    this.propietario,
                    this.departamento,
                    this.pagos
            );
        }
    }
}
