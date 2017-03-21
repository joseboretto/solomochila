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
public class OrganizadorDAO {

    private EntityManager em;
    private BaseDatos bd;

    public OrganizadorDAO() {
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
    
    public List<Organizador> getOrganizadores() {
        Query query = em.createQuery(" SELECT o FROM Organizador o ");
        List<Organizador> l = query.getResultList();
        return l;
    }
    
    public List<Evento> getEventos(Organizador organizador) {
        Query query = em.createQuery(" SELECT E FROM Evento AS E WHERE E.organizador = :organizadorFiltro ");
        query.setParameter("organizadorFiltro", organizador);
        List<Evento> l = query.getResultList();
        return l;
    }
}
