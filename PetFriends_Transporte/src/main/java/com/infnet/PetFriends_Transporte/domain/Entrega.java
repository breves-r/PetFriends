package com.infnet.PetFriends_Transporte.domain;

import com.infnet.PetFriends_Transporte.infra.repository.EnderecoConverter;
import com.infnet.PetFriends_Transporte.infra.repository.StatusEntregaConverter;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Entrega implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long pedidoId;
    @Convert(converter = EnderecoConverter.class)
    private Endereco destino;
    @Convert(converter = StatusEntregaConverter.class)
    private StatusEntrega status;
    @JoinColumn(name = "manifesto_id", referencedColumnName = "id")
    @ManyToOne
    private ManifestoDeTransporte manifestoId;

    public Entrega() {
        this.status = StatusEntrega.EM_ANDAMENTO;
    }

    public Entrega(Long id, Long pedidoId, Endereco destino, ManifestoDeTransporte manifestoId) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.destino = destino;
        this.manifestoId = manifestoId;
        this.status = StatusEntrega.EM_ANDAMENTO;
    }

    public void concluirEntrega() {
        if (this.status == StatusEntrega.EM_ANDAMENTO) {
            this.status = StatusEntrega.CONCLUIDO;
        } else {
            throw new IllegalStateException("Entrega já está concluído ou cancelado.");
        }
    }

    public void cancelarManifesto() {
        if (this.status == StatusEntrega.EM_ANDAMENTO) {
            this.status = StatusEntrega.CANCELADO;
        } else {
            throw new IllegalStateException("Manifesto de transporte já está concluído ou cancelado.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Endereco getDestino() {
        return destino;
    }

    public void setDestino(Endereco destino) {
        this.destino = destino;
    }

    public StatusEntrega getStatus() {
        return status;
    }

    public void setStatus(StatusEntrega status) {
        this.status = status;
    }

    public ManifestoDeTransporte getManifestoId() {
        return manifestoId;
    }

    public void setManifestoId(ManifestoDeTransporte manifestoId) {
        this.manifestoId = manifestoId;
    }
}
