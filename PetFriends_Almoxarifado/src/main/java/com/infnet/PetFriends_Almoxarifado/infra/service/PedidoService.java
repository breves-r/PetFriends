package com.infnet.PetFriends_Almoxarifado.infra.service;

import com.infnet.PetFriends_Almoxarifado.infra.client.Pedido;
import com.infnet.PetFriends_Almoxarifado.infra.client.PedidoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    private static final Logger LOG = LoggerFactory.getLogger(PedidoService.class);
    @Autowired
    private PedidoClient pedidoClient;

    public Pedido getPedidoById(Long id) {
        try {
            return pedidoClient.obterPedidoPorId(id);
        } catch (Exception e) {
            LOG.error("**** ERRO **** " + e.getMessage());
        }
        return null;
    }
}
