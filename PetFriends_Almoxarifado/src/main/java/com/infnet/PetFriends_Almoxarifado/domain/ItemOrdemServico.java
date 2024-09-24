package com.infnet.PetFriends_Almoxarifado.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ItemOrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long productId;
    private Integer quantity;
    @JoinColumn(name = "ORDEM_ID", referencedColumnName = "ID")
    @ManyToOne
    @JsonIgnore
    private OrdemDeServico ordemId;

    public ItemOrdemServico() {
    }

    public ItemOrdemServico(Long id, Long productId, Integer quantity, OrdemDeServico ordemId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.ordemId = ordemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrdemDeServico getOrdemId() {
        return ordemId;
    }

    public void setOrdemId(OrdemDeServico ordemId) {
        this.ordemId = ordemId;
    }

    @Override
    public String toString() {
        return "ItemOrdemServico{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", ordemId=" + ordemId +
                '}';
    }
}
