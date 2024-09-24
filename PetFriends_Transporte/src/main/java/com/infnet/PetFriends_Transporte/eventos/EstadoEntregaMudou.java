package com.infnet.PetFriends_Transporte.eventos;

import java.io.Serializable;
import java.util.Date;

public class EstadoEntregaMudou implements Serializable {
    private Long idPedido;
    private String estado;
    private Date momento;

    public EstadoEntregaMudou() {
    }

    public EstadoEntregaMudou(Long idPedido, String estado) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.momento = new Date();
    }

    public EstadoEntregaMudou(Long idPedido, String estado, Date momento) {
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
        return "EstadoEntregaMudou{" + "idPedido=" + idPedido + ", estado=" + estado + ", momento=" + momento + '}';
    }
}
