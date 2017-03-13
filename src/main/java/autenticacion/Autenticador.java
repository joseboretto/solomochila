/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autenticacion;

/**
 *
 * @author jose
 */
import servicios.LoginREST;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Escalador;
import modelo.Organizador;
import modelo.User;
import modelo.Useriable;
import persistencia.BaseDatos;
import persistencia.EscaladorDAO;
import persistencia.OrganizadorDAO;

public final class Autenticador {

    private static Autenticador authenticator;
    private BaseDatos bd;

    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static final String CLIENT_ID = "79763397676-vi2av2dmg6lfuirsfoll6l1pr46em83m.apps.googleusercontent.com";
    // A user storage which stores <username, password>
    private Map<String, Boolean> usersStorage;

    // A user storage which stores <username, token>
    private Map<String, String> authorizationTokensStorage;

    private Autenticador() {
        System.out.println("----------NUEVO HASH MAP CARGADO");
        authorizationTokensStorage = new HashMap();
        usersStorage = new HashMap();
        cargarHashMap();

    }

    public static Autenticador getInstance() {
        if (authenticator == null) {
            authenticator = new Autenticador();
        }
        return authenticator;
    }

    private String getTokenAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        authorizationTokensStorage.put(authToken, username);
        System.out.println("Nuevo token: " + authToken + " - Valor: " + username);
        return authToken;

    }

    public boolean isAuthTokenValid(String usernameMatch1, String authToken) {
        if (authorizationTokensStorage.containsKey(authToken)) {
            String usernameMatch2 = authorizationTokensStorage.get(authToken);
            if (usernameMatch1.equals(usernameMatch2)) {
                return true;
            }
        }

        return false;
    }

    public void logout(String usernameMatch1, String authToken) throws GeneralSecurityException {

        if (authorizationTokensStorage.containsKey(authToken)) {
            String usernameMatch2 = authorizationTokensStorage.get(authToken);

            if (usernameMatch1.equals(usernameMatch2)) {

                /**
                 * When a client logs out, the authentication token will be
                 * remove and will be made invalid.
                 */
                authorizationTokensStorage.remove(authToken);
                return;
            }

        }

        throw new GeneralSecurityException("Invalid service key and authorization token match.");
    }

    private void cargarHashMap() {
        System.out.println("Contenido HashMap");
        EscaladorDAO escaladorDAO = new EscaladorDAO();
        List<Escalador> resultadoEscaladors = escaladorDAO.getEscaladores();
        for (Escalador escalador : resultadoEscaladors) {
            usersStorage.put(escalador.getEmail(), true);
        }
        OrganizadorDAO organizadorDAO = new OrganizadorDAO();
        List<Organizador> resultadoOrganizadors = organizadorDAO.getOrganizadores();
        for (Organizador organizador : resultadoOrganizadors) {
            usersStorage.put(organizador.getEmail(), false);
        }
    }

    public boolean estaRegistrado(String email) {
        return usersStorage.containsKey(email);
    }

    private void nuevoEscalador(Escalador escalador) {
        bd = new BaseDatos();
        bd.persist(escalador, Escalador.class);
        usersStorage.put(escalador.getEmail(), true);

    }

    private void nuevoOrganizador(Organizador organizador) {
        bd = new BaseDatos();
        bd.persist(organizador, Organizador.class);
        usersStorage.put(organizador.getEmail(), false);

    }

    public Useriable validateGoogleToken(String UserToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID)).build();

        // (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(UserToken);
        } catch (GeneralSecurityException | IOException ex) {
            Logger.getLogger(LoginREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            if (!emailVerified) {
                System.out.println("Invalid ID token.");
                return null;
            }
            String nombre = (String) payload.get("name");
            String apellido = (String) payload.get("family_name");
            return new User(nombre, apellido, email);

        }
        return null;
    }

    public void registrarUsuario(User u, boolean esEscalador) throws GeneralSecurityException {
        if (estaRegistrado(u.getEmail())) {
            throw new GeneralSecurityException("Usuario ya registrado");
        }
        if (esEscalador) {
            nuevoEscalador(new Escalador(u.getNombre(), u.getApellido(), u.getEmail()));
        }
        nuevoOrganizador(new Organizador(u.getNombre(), u.getApellido(), u.getEmail()));

    }

    public String generarToken(Useriable useriable) {
        return getTokenAuth(useriable.getEmail());
    }

    public Boolean esEscalador(String email) {
        return usersStorage.get(email);
    }

}
