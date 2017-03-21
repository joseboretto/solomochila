/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jose
 */
@Entity
public class Organizador implements Serializable, Useriable {
    
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @Id
    private String email;
    
    //JPA only
    public Organizador() {
    }

    
    public Organizador(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Organizador{" + "nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + '}';
    }
    
    
    
    
    
    
    
    
    

}
