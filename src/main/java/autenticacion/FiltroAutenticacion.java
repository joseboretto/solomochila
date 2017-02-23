package autenticacion;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebFilter;
import javax.ws.rs.NotAuthorizedException;

@WebFilter(urlPatterns = "/api/escaladores/*")
public class FiltroAutenticacion implements Filter {

    Autenticador autenticador;

    @Override
    public void init(FilterConfig filterConfig) {
        autenticador = Autenticador.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("Authorization");
        System.out.println("Filtro Auth - Ruta " + request.getPathInfo());
        System.out.println("Filtro Auth - AuthHeader " + authHeader);

        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Error formato Auth header");
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        int posicionSeparador = authHeader.indexOf(":");
        String username = authHeader.substring("Bearer".length(), posicionSeparador).trim();
        String token = authHeader.substring(posicionSeparador + 1, authHeader.length());

        /**
         * FORMATO DE MI AUTENTICACION, EN REALIDAD ESTO ESTA MAL, PERO QUIERO
         * AVANZAR CON OTRAS COSAS Bearer username:token
         *
         */
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        if (autenticador.isAuthTokenValid(username, token)) {
            System.out.println("Token Valido");
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (ServletException ex) {
                System.out.println("No es un error de Autenticacon, tira exeptcion el servlet");
                Logger.getLogger(FiltroAutenticacion.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("Token NO VALIDO");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token NO VALIDO - Filtro");
        }
    }

    @Override
    public void destroy() {
    }

}
