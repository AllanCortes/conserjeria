package cl.ucn.disc.as.model;

import cl.ucn.disc.as.model.exceptions.IllegalDomainException;
import cl.ucn.disc.as.utils.ValidationUtils;
import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
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
public class Departamento extends BaseModel {

    /**
     * The Piso.
     */
    @NotNull
    private Integer piso;

    /**
     * The Número de Piso.
     */
    @NotNull
    private Integer numero;

    /**
     * The Relación Edificio-Departamentos.
     */
    @ManyToOne // Esta anotación indica una relación Many-to-One (muchos departamentos pertenecen a un edificio)
    private Edificio edificio;

    /**
     *Custom builder to validate
     */
    public static class DepartamentoBuilder{

        /**
         * @return The Persona
         */
        public Departamento build(){

            //TODO: Agregar resto de valdiaciones

            return new Departamento(
                    this.piso,
                    this.numero,
                    this.edificio
            );
        }
    }
}
