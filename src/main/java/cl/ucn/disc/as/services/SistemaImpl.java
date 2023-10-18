package cl.ucn.disc.as.services;
import cl.ucn.disc.as.exceptions.SistemaException;
import cl.ucn.disc.as.model.*;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.persistence.PersistenceException;
import java.time.Instant;
import java.util.List;

@Slf4j
public class SistemaImpl implements Sistema {
    public SistemaImpl(Database database){
        this.database = database;
    }
    private final Database database;

    /**
     * The Agregar Edificio
     */
    @Override
    public Edificio add(@NotNull Edificio edificio){
        try {
            this.database.save(edificio);
        } catch (PersistenceException ex) {
            //TODO: save the exception
            log.error("Error", ex);
            throw new SistemaException("Error al agregar un edificio", ex);
        }
        return edificio;
    }
    /**
     * The Agregar Persona
     */
    @Override
    public Persona add(@NotNull Persona persona) {
        try {
            this.database.save(persona);
        } catch (PersistenceException ex) {
            log.error("Error", ex);
            throw new SistemaException("Error al agregar una persona", ex);
        }
        return persona;
    }
    /**
     * The Agregar Departamento
     */
    @Override
    public Departamento addDepartamento(@NotNull Departamento departamento,@NotNull Edificio edificio) {
        try {
            this.database.save(departamento);
            edificio.getDepartamentos().add(departamento);
            log.debug("Edificio afer db: {}", edificio);
            // Accede a la lista de departamentos del edificio
           List<Departamento> listaDeDepartamentos = edificio.getDepartamentos();
            for (Departamento departamentoLista : listaDeDepartamentos) {
                System.out.println("este es un departamento");
                System.out.println("Número de Id: " + departamentoLista.getId());
                System.out.println("Piso: " + departamentoLista.getPiso());
                // Aquí puedes mostrar otros atributos del departamento según sea necesario.
                System.out.println("-----------");
            }
        } catch (PersistenceException ex) {
            log.error("Error", ex);
            throw new SistemaException("Error al agregar un departamento", ex);
        }
        return departamento;
    }
    /**
     * The Agregar Departamento basado en id del Edificio
     */
    @Override
    public Departamento addDepartamento(@NotNull Departamento departamento,@NotNull Long idEdificio) {
        Edificio edificio = this.database.find(Edificio.class, idEdificio);
        if (edificio == null) {
            throw new SistemaException("No se encontró un edificio con ID: ", idEdificio);
        }
        return addDepartamento(departamento, edificio);
    }
    /**
     * The realizar Contrato
     */
    @Override
    public Contrato realizarContrato(@NotNull Persona duenio, @NotNull Departamento departamento, @NotNull Instant fechaPago) {

        Contrato contrato = Contrato.builder()
                .propietario(duenio)
                .departamento(departamento)
                .fechaPago(fechaPago)
                .build();

        Pago pago = Pago.builder()
                .fechaPago(fechaPago)
                .monto(1000)
                .build();

        try {
            this.database.save(contrato);
            this.database.save(pago);
            contrato.getPagos().add(pago);
        } catch (PersistenceException ex) {
            log.error("Error", ex);
            throw new SistemaException("Error al realizar un contrato", ex);
        }
        return contrato;
    }
    /**
     * The realizar Contrato en base al id del departamento y el dueño
     */
    @Override
    public Contrato realizarContrato(@NotNull Long idDuenio,@NotNull Long idDepartamento,@NotNull Instant fechaPago) {
        Persona duenio = this.database.find(Persona.class, idDuenio);
        Departamento departamento = this.database.find(Departamento.class, idDepartamento);

        if (duenio == null) {
            throw new SistemaException("No se encontró una persona con ID: ", idDuenio);
        }
        if (departamento == null) {
            throw new SistemaException("No se encontró un departamento con ID: ", idDepartamento);
        }

        return realizarContrato(duenio, departamento, fechaPago);
    }
    /**
     * The getContratos
     * Obtiene la lista de Contratos
     */
    @Override
    public List<Contrato> getContratos() {
        return this.database.find(Contrato.class).findList();
    }
    /**
     * The getPersonas
     * Obtiene la lista de Personas
     */
    @Override
    public List<Persona> getPersonas() {
        return this.database.find(Persona.class).findList();
    }

    /**
     * The getPagos
     * Obtiene la lista de Pagos
     */
    @Override
    public List<Pago> getPagos(String rut) {
        return this.database.find(Pago.class).where().eq("rut", rut).findList();
    }

    /**
     * The getEdificios
     * Obtiene la lista de Personas
     */
    @Override
    public List<Edificio> getEdificios() {
        return this.database.find(Edificio.class).findList();
    }

    /**
     * The getDepartamentos
     * Obtiene la lista de Personas
     */
    @Override
    public List<Departamento> getDepartamentos() {
        return this.database.find(Departamento.class).findList();
    }

}
