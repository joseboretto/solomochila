/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelo.Boulder;
import modelo.Categoria;
import modelo.Escalador;
import modelo.Evento;
import modelo.Inscripcion;
import modelo.MarcarBoulder;
import modelo.Modalidad;
import modelo.Organizador;

/**
 *
 * @author jose
 */
public class EscaladorDAO {

    private EntityManager em;
    private BaseDatos bd;

    public EscaladorDAO() {
        bd = new BaseDatos();
        em = bd.getEm();
    }
    
    public <T> void persist(Object o, Class<T> clazz) {
        bd.persist(o, clazz);
    }

    public <T> void remove(Object o, Class<T> clazz) {
        bd.remove(o, clazz);
    }

    public <T> T find(Long id, Class<T> clazz) {
        return bd.find(id, clazz);
    }

    public List<Evento> getEventos() {
        Query query = em.createQuery("SELECT e FROM Evento e");
        List<Evento> l = query.getResultList();
        return l;
    }

    public List<Escalador> getEscaladores() {
        Query query = em.createQuery(" SELECT e FROM Escalador e ");
        List<Escalador> l = query.getResultList();
        return l;
    }



    public Escalador getEscacaldarFromDataBase(String email) {
        Query query = em.createQuery(
                " SELECT e "
                + "FROM Escalador e "
                + "WHERE e.email = :email ");
        Escalador escalador = new Escalador();
        escalador.setEmail(email);
        query.setParameter("email", escalador);
        escalador = (Escalador) query.getSingleResult();
        return escalador;
    }

    public List<Inscripcion> getInscripcionEscaldor(Escalador escalador) {
        Query query = em.createQuery(
                " SELECT i "
                + "FROM Inscripcion i "
                + "JOIN i.evento e "
                + "WHERE i.escalador = :email ");
        query.setParameter("email", escalador);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Inscripcion> l = query.getResultList();
        return l;
    }

    public Boolean comprobarInscripcion(Evento evento, Escalador escalador) {
        Query query = em.createQuery(
                " SELECT i "
                + "FROM Inscripcion i "
                + "WHERE i.escalador = :email AND i.evento = :evento ");
        query.setParameter("email", escalador);
        query.setParameter("evento", evento);

        try {
            query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("no esta inscripto");
            return false;
        }
        System.out.println("si esta inscripto");
        return true;
    }

    public MarcarBoulder getMarcaBoulderBy(Escalador escalador, Boulder boulder) {
        Query query = em.createQuery(
                " SELECT m "
                + "FROM MarcarBoulder m "
                + "WHERE m.escalador = :email AND m.boulder = :idBoulder");
        query.setParameter("email", escalador);
        query.setParameter("idBoulder", boulder);
        return (MarcarBoulder) query.getSingleResult();
    }

    public List<Boulder> getMarcasBouldersBy(Escalador escalador, Evento evento) {
        List<Boulder> resultado = new LinkedList();
        Query query = em.createQuery(
                " SELECT m "
                + "FROM MarcarBoulder m "
                + "WHERE m.escalador = :email AND m.boulder.evento = :idEvento");
        query.setParameter("email", escalador);
        query.setParameter("idEvento", evento);
        List<Object> l = query.getResultList();
        for (Object object : l) {
            MarcarBoulder marcarBoulder = (MarcarBoulder) object;
            Boulder boulder = marcarBoulder.getBoulder();
            resultado.add(boulder);
        }

        return resultado;
    }

    

}
