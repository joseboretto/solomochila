/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

/**
 *
 * @author jose
 */
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.security.GeneralSecurityException;
import java.util.List;
import javax.security.auth.login.LoginException;
import modelo.Escalador;
import persistencia.BaseDatos;

public final class Authentication {

    private static Authentication authenticator = null;
    private BaseDatos bd;
    // A user storage which stores <username, password>
    private final Map<String, String> usersStorage;

    // A user storage which stores <username, token>
    private final Map<String, String> authorizationTokensStorage;

    private Authentication() {
        System.out.println("----------NUEVO HASH MAP CARGADO");
        authorizationTokensStorage = new HashMap();
        usersStorage = new HashMap();
        cargarHashMap();

    }

    public static Authentication getInstance() {
        if (authenticator == null) {
            authenticator = new Authentication();
        }
        return authenticator;
    }

    public String getToken(String username) throws LoginException {
        /**
         * Once all params are matched, the authToken will be generated and will
         * be stored in the authorizationTokensStorage. The authToken will be
         * needed for every REST API invocation and is only valid within the
         * login session
         */
        String authToken = UUID.randomUUID().toString();
        authorizationTokensStorage.put(authToken, username);

        return authToken;

    }

    /**
     * The method that pre-validates if the client which invokes the REST API is
     * from a authorized and authenticated source.
     *
     * @param usernameMatch1
     * @param authToken The authorization token generated after login
     * @return TRUE for acceptance and FALSE for denied.
     */
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
        bd = new BaseDatos();
        List<Escalador> resultado = bd.getEscaladores();
        for (Escalador escalador : resultado) {
            usersStorage.put(escalador.getEmail(), escalador.getPassword());
        }
    }

    public boolean estaRegistrado(String email) {
        return usersStorage.containsKey(email);
    }

    public void nuevoEscalador(Escalador escalador) {
        bd = new BaseDatos();
        bd.persist(escalador, Escalador.class);
        usersStorage.put(escalador.getEmail(), escalador.getPassword());

    }
}
