package com.infnet.PetFriends_Almoxarifado.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infnet.PetFriends_Almoxarifado.infra.repository.DescricaoConverter;
import com.infnet.PetFriends_Almoxarifado.infra.repository.OSStatusConverter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrdemDeServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long pedidoId;
    @Convert(converter = DescricaoConverter.class)
    private Descricao descricao;
    @Convert(converter = OSStatusConverter.class)
    private StatusOrdemServico status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    @OneToMany(mappedBy = "ordemId", cascade = CascadeType.ALL)
    private List<ItemOrdemServico> itens;

    public OrdemDeServico() {
        this.status = StatusOrdemServico.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
    }
    public OrdemDeServico(Long pedidoId, String descricao) {
        this.pedidoId = pedidoId;
        this.descricao = new Descricao(descricao);
        this.status = StatusOrdemServico.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
    }

    public OrdemDeServico(Long id, Long pedidoId, String descricao, List<ItemOrdemServico> itens) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.descricao = new Descricao(descricao);
        this.status = StatusOrdemServico.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
        this.itens = itens;
    }

    public void adicionarItem(Long productId, int quantidade) {
        if (this.status != StatusOrdemServico.PENDENTE) {
            throw new IllegalStateException("Não é possível inserir itens em uma OS em andamento");
        }
        ItemOrdemServico itemOrdem = new ItemOrdemServico();
        itemOrdem.setOrdemId(this);
        itemOrdem.setProductId(productId);
        itemOrdem.setQuantity(quantidade);
        if (this.itens == null) {
            this.itens = new ArrayList<>();
        }
        this.itens.add(itemOrdem);
    }

    public void concluirOrdem() {
        if (this.status == StatusOrdemServico.PENDENTE) {
            this.status = StatusOrdemServico.CONCLUIDO;
            this.dataConclusao = LocalDateTime.now();
        } else {
            throw new IllegalStateException("Ordem de serviço já está concluída ou cancelada.");
        }
    }

    public void cancelarOrdem() {
        if (this.status == StatusOrdemServico.PENDENTE) {
            this.status = StatusOrdemServico.CANCELADO;
        } else {
            throw new IllegalStateException("Ordem de serviço já está concluída ou cancelada.");
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

    public Descricao getDescricao() {
        return descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public List<ItemOrdemServico> getItens() {
        return itens;
    }

    public void setItens(List<ItemOrdemServico> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "OrdemDeServico{" +
                "id=" + id +
                ", pedidoId=" + pedidoId +
                ", descricao=" + descricao +
                ", status=" + status +
                ", dataCriacao=" + dataCriacao +
                ", dataConclusao=" + dataConclusao +
                ", itens=" + itens +
                '}';
    }
}
