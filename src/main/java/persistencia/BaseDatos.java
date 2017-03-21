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
public class BaseDatos {

    private static EntityManagerFactory emf;
    private EntityManager em;
    // se pueden mejorar todas las consultas con el criteria API en vez de escribir la consulta
    private static int selector = -1;
    private static final int LOCAl = 1;
    private static final int OpenShift = 2;

    public BaseDatos() {
        initEntityManagerFactory();
        em = emf.createEntityManager();
    }

    private void initEntityManagerFactory() {
        decidirBaseDatos();
        if (emf == null) {
            switch (selector) {
                case LOCAl:
                    localMySQl();
                    break;
                case OpenShift:
                    openShift();
                    break;
            }
        }
    }

    public <T> void persist(Object o, Class<T> clazz) {
        em.getTransaction().begin();
        em.persist(clazz.cast(o));
        em.getTransaction().commit();
    }

    public <T> void remove(Object o, Class<T> clazz) {
        em.getTransaction().begin();
        em.remove(clazz.cast(o));
        em.getTransaction().commit();
    }

    public <T> T find(Object id, Class<T> clazz) {
        return em.find(clazz, id);
    }

    public EntityManager getEm() {
        return em;
    }

    public static void decidirBaseDatos() {
        //se ejecuta una sola vez en toda la vida de la aplicaccion
        if (selector == -1) {
            System.out.println("============  decidirBaseDatos()");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connect = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/solomochila?user=root&password=Enter789&verifyServerCertificate=false&useSSL=false&requireSSL=false");
                selector = LOCAl;
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("============  ERROR CONEXION");
                selector = OpenShift;
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }

    private void openShift() {
        System.out.println("============================= CONFIGURO OPEN SHIFT");
        selector = OpenShift;
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

        Map<String, String> persistenceMap = new HashMap<>();
        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:mysql://" + host + ":" + port + "/solomochila");
        persistenceMap.put("javax.persistence.jdbc.user", user);
        persistenceMap.put("javax.persistence.jdbc.password", password);
        persistenceMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        persistenceMap.put("javax.persistence.schema-generation.database.action", "create-or-extend-tables");

        emf = Persistence.createEntityManagerFactory("PersistenceUnit", persistenceMap);
    }

    private void localMySQl() {
        System.out.println("============================= CONFIGURO local MYSQL");
        selector = LOCAl;
        Map<String, String> persistenceMap = new HashMap<>();
        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/solomochila?verifyServerCertificate=false&useSSL=false&requireSSL=false");
        persistenceMap.put("javax.persistence.jdbc.user", "root");
        persistenceMap.put("javax.persistence.jdbc.password", "Enter789");
        persistenceMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        persistenceMap.put("javax.persistence.schema-generation.database.action", "create-or-extend-tables");

        emf = Persistence.createEntityManagerFactory("PersistenceUnit", persistenceMap);
    }

    public boolean cargarDatos() {
        Query query = em.createQuery("SELECT e FROM Evento e");
        List<Evento> list = query.getResultList();
        if (list.size() > 1) {
            return false;
        }
        Modalidad modalidadBloque = new Modalidad("Bloque");

        Boulder boulder11 = new Boulder(2, 20);
        Boulder boulder12 = new Boulder(3, 30);
        Boulder boulder13 = new Boulder(4, 40);
        Boulder boulder14 = new Boulder(5, 50);
        Boulder boulder15 = new Boulder(6, 60);
        Boulder boulder16 = new Boulder(1, 10);
        Boulder boulder17 = new Boulder(2, 20);
        Boulder boulder18 = new Boulder(3, 30);
        Boulder boulder19 = new Boulder(4, 40);
        Boulder boulder110 = new Boulder(5, 50);
        Boulder boulder111 = new Boulder(6, 60);
        Boulder boulder112 = new Boulder(1, 10);
        Boulder boulder113 = new Boulder(2, 20);
        Boulder boulder114 = new Boulder(3, 30);
        Boulder boulder115 = new Boulder(4, 40);
        Boulder boulder116 = new Boulder(5, 50);
        Boulder boulder117 = new Boulder(6, 60);
        Boulder boulder118 = new Boulder(1, 10);
        Boulder boulder119 = new Boulder(1, 10);
        Boulder boulder120 = new Boulder(1, 10);
        Boulder boulder121 = new Boulder(1, 10);
        Boulder boulder122 = new Boulder(1, 10);
        Boulder boulder123 = new Boulder(1, 10);
        Boulder boulder124 = new Boulder(1, 10);
        Boulder boulder125 = new Boulder(1, 10);
        Boulder boulder126 = new Boulder(1, 10);
        Boulder boulder127 = new Boulder(1, 10);
        Boulder boulder128 = new Boulder(1, 10);
        Boulder boulder129 = new Boulder(1, 10);
        Boulder boulder130 = new Boulder(1, 10);

        Boulder boulder21 = new Boulder(1, 10);
        Boulder boulder22 = new Boulder(2, 20);
        Boulder boulder23 = new Boulder(3, 30);
        Boulder boulder24 = new Boulder(4, 40);

        Boulder boulder31 = new Boulder(1, 3);
        Boulder boulder32 = new Boulder(2, 6);
        Boulder boulder33 = new Boulder(3, 9);

        Boulder boulder41 = new Boulder(1, 10);
        Boulder boulder42 = new Boulder(2, 20);
        Boulder boulder43 = new Boulder(3, 30);
        Boulder boulder44 = new Boulder(4, 40);
        Boulder boulder45 = new Boulder(5, 50);

        List<Boulder> lista1 = new LinkedList();
        lista1.add(boulder11);
        lista1.add(boulder12);
        lista1.add(boulder13);
        lista1.add(boulder14);
        lista1.add(boulder15);
        lista1.add(boulder16);
        lista1.add(boulder17);
        lista1.add(boulder18);
        lista1.add(boulder19);
        lista1.add(boulder110);
        lista1.add(boulder111);
        lista1.add(boulder112);
        lista1.add(boulder113);
        lista1.add(boulder114);
        lista1.add(boulder115);
        lista1.add(boulder116);
        lista1.add(boulder117);
        lista1.add(boulder118);
        lista1.add(boulder119);
        lista1.add(boulder120);
        lista1.add(boulder121);
        lista1.add(boulder122);
        lista1.add(boulder123);
        lista1.add(boulder124);
        lista1.add(boulder125);
        lista1.add(boulder126);
        lista1.add(boulder127);
        lista1.add(boulder128);
        lista1.add(boulder129);
        lista1.add(boulder130);

        List<Boulder> lista2 = new LinkedList();
        lista2.add(boulder21);
        lista2.add(boulder22);
        lista2.add(boulder23);
        lista2.add(boulder24);

        List<Boulder> lista3 = new LinkedList();
        lista3.add(boulder31);
        lista3.add(boulder32);
        lista3.add(boulder33);

        List<Boulder> lista4 = new LinkedList();
        lista4.add(boulder41);
        lista4.add(boulder42);
        lista4.add(boulder43);
        lista4.add(boulder44);
        lista4.add(boulder45);

        Categoria categoriaPrinc = new Categoria("Principiantes");
        Categoria categoriaAvanza = new Categoria("Avanzados");
        List<Categoria> categorias1 = new LinkedList();
        categorias1.add(categoriaAvanza);
        categorias1.add(categoriaPrinc);

        Categoria categoriaMay = new Categoria("Mayores");
        Categoria categoriaMen = new Categoria("Menores");
        List<Categoria> categorias2 = new LinkedList();
        categorias2.add(categoriaMen);
        categorias2.add(categoriaMay);

        Organizador organizador = new Organizador();
        organizador.setEmail("lucio.boretto@gmail.com");
        Evento evento1 = new Evento("Choriboulder", "Cordoba", new Date(), 10, modalidadBloque, lista1, categorias2, organizador);

        Evento evento2 = new Evento("Campeonato Argentino de Escalada Deportiva 2016", "Buenos Aires",
                new Date(1486821420000L), 10, modalidadBloque, lista2, categorias1, organizador);

        Evento evento3 = new Evento("Bari - 1° Encuentro Patagónico Infantil", "Bariloche",
                new Date(1494251820000L), 10, modalidadBloque, lista3, categorias2, organizador);

        Evento evento4 = new Evento("Block Fest Primaveral", "Chubut",
                new Date(1552226220000L), 10, modalidadBloque, lista4, categorias1, organizador);

        

        em.getTransaction().begin();
        em.persist(evento1);
        em.persist(evento2);
        em.persist(evento3);
        em.persist(evento4);
        em.persist(organizador);
        em.getTransaction().commit();

        return true;
    }

}
