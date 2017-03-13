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
import modelo.MarcarBoulder;
import persistencia.BaseDatos;
import persistencia.EscaladorDAO;

/**
 *
 * @author jose
 */
public class MarcarBoulderResource {

    private final Escalador escalador;
    private final BaseDatos bd;
    private final Evento evento;
    private Boulder boulder;
    private EscaladorDAO escaladorDAO;

    public MarcarBoulderResource(Escalador escalador, Evento evento, String idBoulder) {
        this.escalador = escalador;
        this.evento = evento;
        bd = new BaseDatos();
        escaladorDAO = new EscaladorDAO();
        boulder = new Boulder();
        boulder.setId(new Long(idBoulder));
    }

    @POST
    public void marcarBoulderX(@PathParam("idBoulder") String idBoulder) {
        MarcarBoulder marca = new MarcarBoulder(escalador, boulder, evento);
        bd.persist(marca, MarcarBoulder.class);
    }

    @DELETE
    public void desmarcarBoulderX(@PathParam("idBoulder") String idBoulder) {
//        boulder = new Boulder();
//        boulder.setId(new Long(idBoulder));
        MarcarBoulder m = escaladorDAO.getMarcaBoulderBy(escalador, boulder);
        bd.remove(m, MarcarBoulder.class);
    }

}
