/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.Date;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelo.Boulder;
import modelo.Categoria;
import modelo.Escalador;
import modelo.Evento;
import modelo.Inscripcion;
import persistencia.BaseDatos;
import persistencia.EscaladorDAO;

/**
 *
 * @author jose
 */
public class EventoEscalador {

    private final Escalador escalador;
    private final EscaladorDAO EscaladorDAO;
    private final Evento evento;

    public EventoEscalador(Escalador escalador, String idEvento) {
        System.out.println("new EventoResource");
        this.escalador = escalador;
        EscaladorDAO = new EscaladorDAO();
        evento = new Evento();
        evento.setId(new Long(idEvento));
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String comprobarInscripcion() {
        System.out.println("comprobar inscripcion");
        return "{ \"resultado\" : \"" + EscaladorDAO.comprobarInscripcion(evento, escalador) + "\" }";
    }

    @POST
    @Path("categorias/{idCategoria}")
    @Produces({MediaType.APPLICATION_JSON})
    public void inscrbir(@PathParam("idCategoria") String idCategoria) {
        System.out.println("inscribir");
        Categoria categoria = new Categoria();
        categoria.setId(new Long(idCategoria));
        Inscripcion inscripcion = new Inscripcion(new Date(), escalador, categoria, evento);
        EscaladorDAO.persist(inscripcion, Inscripcion.class);
    }

    @DELETE
    @Path("inscripcion/{idInscripcion}")
    public void eliminarIncripcion(@PathParam("idInscripcion") String idInscripcion) {
        System.out.println("eliminar inscribir");
        //debo elimianr las marcas tambien
        Inscripcion inscripcion = EscaladorDAO.find(new Long(idInscripcion), Inscripcion.class);
        EscaladorDAO.remove(inscripcion, Inscripcion.class);
    }

    @GET
    @Path("terminarBoulder")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Boulder> getBoulderTerminados() {
        return EscaladorDAO.getMarcasBouldersBy(escalador, evento);
    }

    @Path("terminarBoulder/{idBoulder}")
    @Produces({MediaType.APPLICATION_JSON})
    public MarcarBoulderResource marcarBoulder(@PathParam("idBoulder") String idBoulder) {
        return new MarcarBoulderResource(escalador, evento, idBoulder);
    }

}
