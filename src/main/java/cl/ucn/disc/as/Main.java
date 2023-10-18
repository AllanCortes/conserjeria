package cl.ucn.disc.as;

import cl.ucn.disc.as.dao.PersonaFinder;
import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.model.Edificio;
import cl.ucn.disc.as.model.Departamento;
import cl.ucn.disc.as.model.Contrato;
import cl.ucn.disc.as.model.Pago;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.ExpressionList;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
/**
 * The main.
 * @author Allan Cortes Cortes
 */
@Slf4j
public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        //Obtener la BD
        Database db = DB.getDefault();

        //Inicializar el sistema
        Sistema sistema = new SistemaImpl(db);

        boolean programaActivo = true;

        do {

            log.debug("Menú del Sistema");
            log.debug("1. Crear un edificio");
            log.debug("2. Agregar un departamento a un edificio");
            log.debug("3. Agregar un dueño");
            log.debug("4. Crear un contrato entre un dueño y la constructora");
            log.debug("5. Obtener listado de dueños morosos");
            log.debug("0. Salir");
            log.debug("Elige una opción: ");

            int opcion = scanner.nextInt();

            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    log.debug("Ingrese nombre del edificio");
                    String nombre_edificio = scanner.nextLine();
                    log.debug("Ingrese direccion del edificio");
                    String direccion = scanner.nextLine();
                    log.debug("Ingrese cantidad pisos del edificio");
                    int numero_pisos = scanner.nextInt();

                    // Crear edificio
                    Edificio edificio = Edificio.builder()
                            .nombre(nombre_edificio)
                            .direccion(direccion)
                            .numero_pisos(numero_pisos)
                            .build();
                    sistema.add(edificio);
                }
                case 2 -> {

                    // Obtener la lista de edificios desde el sistema
                    List<Edificio> listaDeEdificios = sistema.getEdificios();
                    if (listaDeEdificios.isEmpty()) {
                        log.debug("No es posible agregar departamentos sin edificios");
                        break;
                    }
                    //imprimir la lista de edificios
                    for (Edificio edificioLista : listaDeEdificios) {
                        System.out.println("Id del edificio: " + edificioLista.getId());
                        System.out.println("Nombre del edificio: " + edificioLista.getNombre());
                        System.out.println("Dirección del edificio: " + edificioLista.getDireccion());
                        System.out.println("---------------------------------------------------------");
                    }
                    log.debug("Ingrese ID del edificio al cual se le agregará el departamento");
                    long id = scanner.nextInt();

                    log.debug("Ingrese numero del departamento");
                    int numero = scanner.nextInt();

                    log.debug("Ingrese piso del departamento");
                    int piso = scanner.nextInt();

                    // Crear un departamento y agregarlo al edificio usando el builder de Departamento
                    Departamento departamento = Departamento.builder()
                            .numero(numero)
                            .piso(piso)
                            .build();

                    sistema.addDepartamento(departamento, id);
                }
                case 3 -> {
                    log.debug("Ingrese Rut de la persona");
                    String rut = scanner.nextLine();

                    log.debug("Ingrese Nombre");
                    String nombre = scanner.nextLine();

                    log.debug("Ingrese Apellidos");
                    String apellidos = scanner.nextLine();

                    log.debug("Ingrese Email");
                    String email = scanner.nextLine();

                    log.debug("Ingrese telefono");
                    String telefono = scanner.nextLine();

                    Persona persona = Persona.builder()
                            .rut(rut)
                            .nombre(nombre)
                            .apellidos(apellidos)
                            .email(email)
                            .telefono(telefono)
                            .build();

                    sistema.add(persona);
                }
                case 4 -> {
                    // Obtener la lista de personas desde el sistema
                    List<Persona> listaDePersonas = sistema.getPersonas();
                    if (listaDePersonas.isEmpty()) {
                        log.debug("No es posible realizar contrato sin Personas");
                        break;
                    }
                    //imprimir la lista de personas
                    for (Persona personaLista : listaDePersonas) {
                        System.out.println("Id de la persona: " + personaLista.getId());
                        System.out.println("Nombre de la persona: " + personaLista.getNombre());
                        System.out.println("---------------------------------------------------------");
                    }
                    log.debug("Ingrese ID de la persona a la cual se le aplicara un contrato");
                    long id_persona= scanner.nextInt();

                    // Obtener la lista de departamentos desde el sistema
                    List<Departamento> listaDeDepartamentos = sistema.getDepartamentos();
                    if (listaDeDepartamentos.isEmpty()) {
                        log.debug("No es posible agregar departamentos sin edificios");
                        break;
                    }
                    //imprimir la lista de departamentos
                    for (Departamento departamentoLista : listaDeDepartamentos) {
                        System.out.println("Id del departamento: " + departamentoLista.getId());
                        System.out.println("Numero del departamento: " + departamentoLista.getNumero());
                        System.out.println("Piso del departamento: " + departamentoLista.getPiso());
                        System.out.println("---------------------------------------------------------");
                    }
                    log.debug("Ingrese ID del departamento al cual se hara contrato");
                    long id_edificio = scanner.nextInt();

                    sistema.realizarContrato(id_persona,id_edificio,Instant.now());
                }
                case 5 -> {
                    //TODO: Obtener un listado con el nombre del dueño, datos del departamento y monto adeudado de dueños morosos.
                }
                case 0 -> {
                    programaActivo = false;
                    System.out.println("Saliendo del programa.");
                }
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (programaActivo);

    }
}
