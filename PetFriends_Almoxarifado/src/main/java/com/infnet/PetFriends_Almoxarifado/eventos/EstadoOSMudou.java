package com.infnet.PetFriends_Almoxarifado.eventos;

import java.io.Serializable;
import java.util.Date;

public class EstadoOSMudou implements Serializable {

    private Long idPedido;
    private String estado;
    private Date momento;

    public EstadoOSMudou() {
    }

    public EstadoOSMudou(Long idPedido, String estado) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.momento = new Date();
    }

    public EstadoOSMudou(Long idPedido, String estado, Date momento) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.momento = momento;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getMomento() {
        return momento;
    }

    public void setMomento(Date momento) {
        this.momento = momento;
    }

    @Override
    public String toString() {
        return "EstadoOSMudou{" + "idPedido=" + idPedido + ", estado=" + estado + ", momento=" + momento + '}';
    }
}
