/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import autenticacion.Autenticador;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Escalador;
import modelo.User;
import modelo.Useriable;

/**
 * REST Web Service
 *
 * @author jose
 */
@Path("/authentication")
public class LoginREST {

    Autenticador authenticator = Autenticador.getInstance();

    /**
     * @see https://developers.google.com/identity/sign-in/web/backend-auth
     * @param UserToken
     * @return
     */
    @POST
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("googleSingIn")
    public Response googleSingIn(String token) throws Exception {
        Useriable user = authenticator.validateGoogleToken(token);
        if (user != null) {
            if (authenticator.estaRegistrado(user.getEmail())) {

                String tokenAuth = authenticator.generarToken(user);
                String jsonToken = "{ \"token\" : \"" + tokenAuth + "\" ,"
                        + "\"email\" : \"" + user.getEmail() + "\" ,"
                        + "\"esEscalador\" : \"" + authenticator.esEscalador(user.getEmail()) + "\" }";
                return Response.ok(jsonToken).build();
            } else {
                //409
                return Response.status(Response.Status.CONFLICT).entity(user).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("registrar/{tipoUsuario}")
    public Response registrarNuevoUsuario(@PathParam("tipoUsuario") String tipoUsuario, User user) throws Exception {
        System.out.println("nuevo usuario");
        System.out.println(user);
        authenticator = Autenticador.getInstance();
        authenticator.registrarUsuario(user, tipoUsuario.equals(true));
        String tokenAuth = authenticator.generarToken(user);
        String jsonToken = "{ \"token\" : \"" + tokenAuth + "\" ,"
                + "\"email\" : \"" + user.getEmail() + "\" }";
        return Response.ok(jsonToken).build();

    }

}
