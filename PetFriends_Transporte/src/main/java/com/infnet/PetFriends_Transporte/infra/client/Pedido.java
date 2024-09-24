package com.infnet.PetFriends_Transporte.infra.client;

import java.util.List;

public class Pedido {
    private Long ID;
    private Long customerId;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
