package com.infnet.PetFriends_Transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infnet.PetFriends_Transporte.infra.repository.EnderecoConverter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ManifestoDeTransporte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private LocalDate dataSaida;
    private String motorista;
    private String placaVeiculo;
    @Convert(converter = EnderecoConverter.class)
    private Endereco origem;
    @Convert(converter = EnderecoConverter.class)
    private Endereco destinoFinal;
    @JsonIgnoreProperties(value = "manifestoId")
    @OneToMany(mappedBy = "manifestoId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Entrega> entregas;
    private boolean emEdicao;

    public ManifestoDeTransporte() {
        this.dataSaida = LocalDate.now();
        this.emEdicao = true;
    }

    public ManifestoDeTransporte(String motorista, String placaVeiculo, Endereco origem, Endereco destinoFinal) {
        this.motorista = motorista;
        this.placaVeiculo = placaVeiculo;
        this.origem = origem;
        this.destinoFinal = destinoFinal;
        this.dataSaida = LocalDate.now();
        this.emEdicao = true;
    }

    public ManifestoDeTransporte(Long id, String motorista, String placaVeiculo, Endereco origem, Endereco destinoFinal, List<Entrega> entregas) {
        this.id = id;
        this.motorista = motorista;
        this.placaVeiculo = placaVeiculo;
        this.origem = origem;
        this.destinoFinal = destinoFinal;
        this.entregas = entregas;
        this.dataSaida = LocalDate.now();
        this.emEdicao = true;
    }

    public void adicionarEntrega(Long pedidoId, Endereco destino) {
        if (pedidoId == null) {
            throw new IllegalArgumentException("Pedido inválido");
        }
        if (destino == null) {
            throw new IllegalArgumentException("Endereço inválido");
        }

        Entrega entrega = new Entrega();
        entrega.setPedidoId(pedidoId);
        entrega.setDestino(destino);
        entrega.setManifestoId(this);
        if (this.entregas == null) {
            this.entregas = new ArrayList<>();
        }
        this.entregas.add(entrega);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Endereco getOrigem() {
        return origem;
    }

    public void setOrigem(Endereco origem) {
        this.origem = origem;
    }

    public Endereco getDestinoFinal() {
        return destinoFinal;
    }

    public void setDestinoFinal(Endereco destinoFinal) {
        this.destinoFinal = destinoFinal;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Entrega> entregas) {
        this.entregas = entregas;
    }

    public boolean isEmEdicao() {
        return emEdicao;
    }

    public void setEmEdicao(boolean emEdicao) {
        this.emEdicao = emEdicao;
    }
}
