/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autenticacion;

import modelo.Escalador;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jose
 */
public class AutenticadorTest {
    private Autenticador autenticador;
    public AutenticadorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        autenticador = Autenticador.getInstance();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void loginExitoso() {
         Escalador e = new Escalador("test", "test", "test@gmail.com");
         String token = autenticador.generarToken(e);
         boolean esValido = autenticador.isAuthTokenValid(e.getEmail(), token);
         assertTrue(esValido);
     }
     
     @Test
     public void loginError() {
         Escalador e = new Escalador("test", "test", "test@gmail.com");
         String token = autenticador.generarToken(e);
         //trato de entrar a otro email con mi token
         boolean esValido = autenticador.isAuthTokenValid("joseboretto@gmail.", token);
         assertFalse(esValido);
     }
}
