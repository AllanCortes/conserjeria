/** .
 * Copyright (c) 2023. Arquitectura de Software, DISC, UCN
 */
package cl.ucn.disc.as.ui;
import io.javalin.Javalin;

/**
 * The Routes config.
 *
 * @author Arquitectura de Software
 */
public interface RoutesConfigurator {

    /**
     * Configure the routes
     *
     * @param javalin to use.
     */
    void configure (Javalin javalin);
}
