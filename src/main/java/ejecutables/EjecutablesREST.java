/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecutables;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import persistencia.BaseDatos;

/**
 *
 * @author jose
 */


@Path("test")
public class EjecutablesREST {

    @Path("ping")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String ping() {
        return " Ping : " + new Date();
    }

    @Path("insert")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String todosLosEventosInscriptos() {
        BaseDatos bd = new BaseDatos();
        return "Se guardaron?" +  bd.insert();
    }

    @Path("env")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String env() {
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        String url = System.getenv("OPENSHIFT_MYSQL_DB_URL");

        return "Host: " + host + ", Puerto: " + port + ", User: " + user + ", Pswd:" + password+ ", URL:" + url;
    }

    @Path("persist")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String persist() {
        
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        
        Map<String, String> persistenceMap = new HashMap<String, String>();

        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:mysql://"+ host + ":"+ port + "/solomochila");
        persistenceMap.put("javax.persistence.jdbc.user", user);
        persistenceMap.put("javax.persistence.jdbc.password", password);
        persistenceMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");

        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("OpenShiftJPA", persistenceMap);
        EntityManager manager = managerFactory.createEntityManager();
        return "persist";
    }
    
    @Path("db")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String db() {
        String result = "Datos ";
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("OpenShiftJPA");
        Map<String,Object> props = managerFactory.getProperties();
        for (Map.Entry<String, Object> entry : props.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            result += "/n Key "+ key + "value" + value;
        }
        return result;
    }

}
