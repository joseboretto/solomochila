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
import persistencia.BaseDatos;

public final class Autenticador {

    private static Autenticador authenticator = null;
    private BaseDatos bd;

    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static final String CLIENT_ID = "79763397676-vi2av2dmg6lfuirsfoll6l1pr46em83m.apps.googleusercontent.com";
    // A user storage which stores <username, password>
    private final Map<String, String> usersStorage;

    // A user storage which stores <username, token>
    private final Map<String, String> authorizationTokensStorage;

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
        bd = new BaseDatos();
        List<Escalador> resultado = bd.getEscaladores();
        for (Escalador escalador : resultado) {
            System.out.println("Clave: " + escalador.getEmail());
            usersStorage.put(escalador.getEmail(), "");
        }
    }

    private boolean estaRegistrado(String email) {
        return usersStorage.containsKey(email);
    }

    private void nuevoEscalador(Escalador escalador) {
        bd = new BaseDatos();
        bd.persist(escalador, Escalador.class);
        usersStorage.put(escalador.getEmail(), "");

    }

    public Escalador validateGoogleToken(String UserToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID)).build();

        // (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(UserToken);
        } catch (GeneralSecurityException | IOException ex) {
            Logger.getLogger(ServicioLogin.class.getName()).log(Level.SEVERE, null, ex);
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
            String name = (String) payload.get("name");
            String familyName = (String) payload.get("family_name");
            Escalador escalador = new Escalador(name, familyName, email);

            return escalador;
        }
        return null;
    }

    public String registrarYgetToken(Escalador escalador) {
        //si no esta registrado lo agrego a la BD
        if (!estaRegistrado(escalador.getEmail())) {
            nuevoEscalador(escalador);
        }
        return getTokenAuth(escalador.getEmail());
    }

}
