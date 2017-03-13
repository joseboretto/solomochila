/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.Set;
import javax.ejb.Singleton;
import javax.ws.rs.core.Application;

/**
 *
 * @author jose
 */
@Singleton
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ejecutables.EjecutablesREST.class);
        resources.add(servicios.EscaladorREST.class);
        resources.add(servicios.EventoREST.class);
        resources.add(servicios.JsonMoxyConfigurationContextResolver.class);
        resources.add(servicios.LoginREST.class);
        resources.add(servicios.MarcarBoulderResource.class);
        resources.add(servicios.OrganizadorREST.class);
    }

}
