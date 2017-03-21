/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jose
 */
@Entity
public class Evento implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private float precioInscripcion;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Modalidad modalidad;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Boulder> boulders;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Categoria> categorias;
    private Organizador organizador;

    public Evento(String nombre, String direccion, Date fecha, float precioInscripcion, Modalidad modalidad, List<Boulder> boulders, List<Categoria> categorias, Organizador organizador) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fecha = fecha;
        this.precioInscripcion = precioInscripcion;
        this.modalidad = modalidad;
        this.boulders = boulders;
        this.categorias = categorias;
        this.organizador = organizador;
    }

    public Evento() {
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    

    
    public float getPrecioInscripcion() {
        return precioInscripcion;
    }

    public void setPrecioInscripcion(float precioInscripcion) {
        this.precioInscripcion = precioInscripcion;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    //@XmlTransient
    public List<Boulder> getBoulders() {
        return boulders;
    }

    public void setBoulders(List<Boulder> boulders) {
        this.boulders = boulders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }
    
    

    @Override
    public String toString() {
        String boulString = "";
        for (Boulder boulder : boulders) {
            boulString += boulder.toString();
        }
        
        String catString = "";
        for (Categoria cat : categorias) {
            catString += cat.toString();
        }
        return "Evento{" + "id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", fecha=" + fecha + ", precioInscripcion=" + precioInscripcion + ", modalidad=" + modalidad +  ", categorias=" + catString+ '}' + boulString + ", organizador= " + organizador;
    }

 
    
    
    

}
