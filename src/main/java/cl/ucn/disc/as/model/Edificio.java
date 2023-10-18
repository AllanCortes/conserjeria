package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * The Persona class.
 *
 * @author Allan Cortes Cortes.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Edificio extends BaseModel{

    /**
     * The Nombre.
     */
    @NotNull
    @Getter
    private String nombre;

    /**
     * The Dirección.
     */
    @NotNull
    @Getter
    private String direccion;

    /**
     * The Número de Pisos.
     */
    @NotNull
    @Getter
    private Integer numero_pisos;

    /**
     * The Lista departamentos.
     */
    @Getter
    @OneToMany(mappedBy = "edificio", cascade = CascadeType.ALL)
    private List<Departamento> departamentos;

    /**
     *Custom builder to validate
     */
    public static class EdificioBuilder{

        /**
         * @return The Persona
         */
        public Edificio build(){

            //TODO: Agregar resto de valdiaciones

            return new Edificio(
                    this.nombre,
                    this.direccion,
                    this.numero_pisos,
                    this.departamentos
            );
        }
    }
    public void add(Departamento departamento) {
        this.departamentos.add(departamento);
    }

}
