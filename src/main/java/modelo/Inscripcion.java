/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jose
 */
@Entity
public class Inscripcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    
    @Temporal(TemporalType.DATE)
    private Date fechaTransaccion;
    private boolean estaAcreditada;
    @OneToOne( cascade = CascadeType.MERGE)
    private Escalador escalador;
    private Categoria categoria;
    private Evento evento;
            

    public Inscripcion() {
    }

    public Inscripcion(Date fechaTransaccion, Escalador escalador, Categoria categoria, Evento evento) {
        this.fechaTransaccion = fechaTransaccion;
        this.estaAcreditada = false;
        this.escalador = escalador;
        this.categoria = categoria;
        this.evento = evento;
    }

 
    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    

    public boolean isEstaAcreditada() {
        return estaAcreditada;
    }

    public void setEstaAcreditada(boolean estaAcreditada) {
        this.estaAcreditada = estaAcreditada;
    }

    public Escalador getEscalador() {
        return escalador;
    }

    public void setEscalador(Escalador escalador) {
        this.escalador = escalador;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "id=" + id + ", fechaTransaccion=" + fechaTransaccion + ", estaAcreditada=" + estaAcreditada + ", escalador=" + escalador + ", categoria=" + categoria + ", evento=" + evento + '}';
    }
    
    
    
    
}
