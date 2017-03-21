/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author jose
 */
@Entity
public class MarcarBoulder implements Serializable {

    @Id
    private Escalador escalador;
    @Id
    private Boulder boulder;
    //como necesito recuperar todos los boulders de un evento agrego la relacion evento, pero no se si testa bien
    private boolean estaTerminado;
    
    
    public MarcarBoulder() {
    }

    public MarcarBoulder(Escalador escalador, Boulder boulder) {
        this.escalador = escalador;
        this.boulder = boulder;
        this.estaTerminado = true;

    }

    public void terminar() {
        estaTerminado = true;
    }

    public void iniciar() {
        estaTerminado = true;
    }

    public int getPuntaje() {
        return boulder.getPuntaje();
    }

    public Escalador getEscalador() {
        return escalador;
    }

    public Boulder getBoulder() {
        return boulder;
    }

    public void setBoulder(Boulder boulder) {
        this.boulder = boulder;
    }

    public boolean isEstaTerminado() {
        return estaTerminado;
    }

    public void setEstaTerminado(boolean estaTerminado) {
        this.estaTerminado = estaTerminado;
    }


    
    

    @Override
    public String toString() {
        return "MarcarBoulder{" + "escalador=" + escalador + ", boulder=" + boulder + ", estaTerminado=" + estaTerminado;
    }

}
