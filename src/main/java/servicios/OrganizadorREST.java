/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelo.Escalador;
import modelo.Evento;
import modelo.Inscripcion;
import modelo.Organizador;
import persistencia.BaseDatos;
import persistencia.OrganizadorDAO;

/**
 *
 * @author jose
 */
//@Stateless
@Path("organizadores/{idOrganizador}")
public class OrganizadorREST {

    @PathParam("idOrganizador")
    private String idOrganizador;
    private Organizador organizador;

    public OrganizadorREST() {
//        System.out.println("newEsaladorREST()");
        //COMO NO ENTINEDO EL CICLO DE VIDA DE LOS SERVLETS ME DA NULL POINTER
//        escalador = new Escalador();
//        escalador.setEmail(idEscalador);
    }

    @GET
    @Path("/eventos/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Evento> getEventosCreados() {
        organizador = new Organizador();
        organizador.setEmail(idOrganizador);
        return new OrganizadorDAO().getEventos(organizador);
    }

    @Path("/eventos/{idEvento}")
    public Object eventos(@PathParam("idEvento") String idEvento) {
        organizador = new Organizador();
        organizador.setEmail(idOrganizador);
        return new EventoOrganizador();
    }

    @POST
    @Path("/eventos/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public void registrarEvento(Evento e) {
        organizador = new Organizador();
        organizador.setEmail(idOrganizador);
        
    }
}
