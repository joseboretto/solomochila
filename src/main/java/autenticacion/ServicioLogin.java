/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autenticacion;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Escalador;

/**
 * REST Web Service
 *
 * @author jose
 */
@Path("/authentication")
public class ServicioLogin {

    Autenticador authenticator;

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
        authenticator = Autenticador.getInstance();
        //primero valido el token de google
        Escalador escalador = authenticator.validateGoogleToken(UserToken);
        if (escalador != null) {
            String tokenAuth = authenticator.registrarYgetToken(escalador);
            String jsonToken = "{ \"token\" : \"" + tokenAuth + "\" }";
            return Response.ok(jsonToken).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
