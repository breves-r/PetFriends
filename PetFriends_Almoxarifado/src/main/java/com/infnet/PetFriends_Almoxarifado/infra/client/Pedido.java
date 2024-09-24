package com.infnet.PetFriends_Almoxarifado.infra.client;

import com.infnet.PetFriends_Almoxarifado.domain.ItemOrdemServico;

import java.util.List;

public class Pedido {
    private Long ID;
    private List<ItemOrdemServico> itemList;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public List<ItemOrdemServico> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemOrdemServico> itemList) {
        this.itemList = itemList;
    }
}
