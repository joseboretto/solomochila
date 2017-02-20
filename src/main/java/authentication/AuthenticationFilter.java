package authentication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebFilter;
import javax.ws.rs.NotAuthorizedException;
import modelo.Escalador;
import persistencia.BaseDatos;

@WebFilter(urlPatterns = "/api/escaladores/*")
public class AuthenticationFilter implements Filter {

    private BaseDatos bd;
    Authentication demoAuthenticator;
    private static final Logger LOGGER = Logger.getLogger( AuthenticationFilter.class.getName() );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        bd = new BaseDatos();
        demoAuthenticator = Authentication.getInstance();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("Ruta filtro  Auth " + request.getPathInfo());
        LOGGER.log( Level.WARNING, "FILTRO LOGGER", request.getPathInfo() );
        LOGGER.log(Level.INFO, "iNFO");

        String authHeader = request.getHeader("Authorization");
        System.out.println("AuthHeader " + authHeader);

        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        int posicionSeparador = authHeader.indexOf(":");
        String username = authHeader.substring("Bearer".length(), posicionSeparador).trim();
        String token = authHeader.substring(posicionSeparador + 1, authHeader.length());
        System.out.println("Username: " + username + " token: " + token);

        /**
         * FORMATO DE MI AUTENTICACION, EN REALIDAD ESTO ESTA MAL, PERO QUIERO
         * AVANZAR CON OTRAS COSAS Bearer username:token
         *
         */
        try {

            // Validate the token
            validateToken(username, token, response);
        } catch (Exception e) {
            System.out.println("Auth Error token invalido");
            e.printStackTrace();
            unauthorized(response, "token invalido");
        }

        try {
            //deberia encadenar los filtro, esto es un parche, una solucion rapita

            //devuelve la request al metodo
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ServletException ex) {
            System.out.println("No es un error de uatenticacon, tira exeptcion el metodo");
            ex.printStackTrace();
            unauthorized(response, "error en do filter");

        }
    }

    private void validateToken(String username, String token, HttpServletResponse response) throws Exception {
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        if (!demoAuthenticator.isAuthTokenValid(username, token)) {
            unauthorized(response);
        }

    }

    @Override
    public void destroy() {
    }

    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        System.out.println("No autorizado");
        //te pide ususairo y contrasenia pero yo no uso ese metodo de autenticacion
        //response.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");
        response.sendError(401, message);
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        unauthorized(response, "Unauthorized");
    }

    public Escalador getEscacaldarFromDataBase(String email) {
        return bd.getEscacaldarFromDataBase(email);
    }

}
