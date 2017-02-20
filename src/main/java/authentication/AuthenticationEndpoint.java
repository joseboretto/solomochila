/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Escalador;

/**
 * REST Web Service
 *
 * @author jose
 */
@Path("/authentication")
public class AuthenticationEndpoint {

    Authentication demoAuthenticator;
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static final String CLIENT_ID = "79763397676-vi2av2dmg6lfuirsfoll6l1pr46em83m.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "client secret here";

    /**
     * @see https://developers.google.com/identity/sign-in/web/backend-auth
     * @param UserToken
     * @return
     */
    @POST
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("googleSingIn")
    public Response googleSingIn(String UserToken) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID)).build();
        // Or, if multiple clients access the backend:
        //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))

        // (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(UserToken);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(AuthenticationEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuthenticationEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            if (!emailVerified) {
                System.out.println("Invalid ID token.");
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String name = (String) payload.get("name");
            String familyName = (String) payload.get("family_name");
            Escalador escalador = new Escalador(name, familyName, email, userId);

            String tokenAcceso = authenticateGoogle(escalador);
            String jsonToken = "{ \"token\" : \"" + tokenAcceso + "\" }";
            return Response.ok(jsonToken).build();

            // Use or store profile information
            // ...
        } else {
            System.out.println("Invalid ID token.");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    private String authenticateGoogle(Escalador escalador) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        demoAuthenticator = Authentication.getInstance();
        //si no esta registrado lo agrego a la BD
        if (!demoAuthenticator.estaRegistrado(escalador.getEmail())) {
            demoAuthenticator.nuevoEscalador(escalador);

        }
        return demoAuthenticator.getToken(escalador.getEmail());
    }

}
