/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelo.Escalador;
import modelo.Inscripcion;
import persistencia.BaseDatos;

/**
 *
 * @author jose
 */
//@Stateless
@Path("escaladores/{idEscalador}")
public class EscaladorREST {

    @PathParam("idEscalador")
    private String idEscalador;
    private Escalador escalador;

    public EscaladorREST() {
//        System.out.println("newEsaladorREST()");
        //COMO NO ENTINEDO EL CICLO DE VIDA DE LOS SERVLETS ME DA NULL POINTER
//        escalador = new Escalador();
//        escalador.setEmail(idEscalador);
    }

    @GET
    @Path("/eventos/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Inscripcion> getInscripciones() {
        escalador = new Escalador();
        escalador.setEmail(idEscalador);
//        System.out.println("getInscripciones() - " + escalador.getEmail());
        BaseDatos bd = new BaseDatos();
        return bd.getInscripcionEscaldor(escalador);
    }

    @Path("/eventos/{idEvento}")
    public Object eventos(@PathParam("idEvento") String idEvento) {
        escalador = new Escalador();
        escalador.setEmail(idEscalador);
//        System.out.println("eventos() - " + escalador.getEmail());
        return new EventoResource(escalador, idEvento);
    }
}
