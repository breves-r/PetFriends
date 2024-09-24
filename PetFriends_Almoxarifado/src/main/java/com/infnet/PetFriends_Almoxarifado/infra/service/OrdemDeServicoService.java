package com.infnet.PetFriends_Almoxarifado.infra.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.google.cloud.spring.pubsub.support.converter.ConvertedBasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.infnet.PetFriends_Almoxarifado.domain.ItemOrdemServico;
import com.infnet.PetFriends_Almoxarifado.domain.OrdemDeServico;
import com.infnet.PetFriends_Almoxarifado.eventos.EstadoOSMudou;
import com.infnet.PetFriends_Almoxarifado.infra.client.Pedido;
import com.infnet.PetFriends_Almoxarifado.infra.repository.OrdemDeServicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrdemDeServicoService {
    private static final Logger LOG = LoggerFactory.getLogger(OrdemDeServicoService.class);
    @Autowired
    private PubSubTemplate pubSubTemplate;
    @Autowired
    JacksonPubSubMessageConverter converter;
    @Autowired
    private OrdemDeServicoRepository repository;
    @Autowired
    private PedidoService pedidoService;

    public OrdemDeServico save(OrdemDeServico ordemDeServico) {
        try {
            return repository.save(ordemDeServico);
        } catch (Exception e) {
            LOG.error("**** ERRO **** " + e.getMessage());
        }
        return null;
    }

    private void criarOrdemDeServico(Pedido pedido) {
        OrdemDeServico ordemDeServico = new OrdemDeServico(pedido.getID(), "Ordem de Serviço do Pedido " + pedido.getID());
        for(ItemOrdemServico item : pedido.getItemList()) {
            ordemDeServico.adicionarItem(item.getProductId(), item.getQuantity());
        }
        this.save(ordemDeServico);
    }

    public void processarEvento(EstadoOSMudou evento) {
        if(Objects.equals(evento.getEstado(), "FECHADO")) {
            Pedido pedido = pedidoService.getPedidoById(evento.getIdPedido());
            this.criarOrdemDeServico(pedido);
            this.enviar(new EstadoOSMudou(evento.getIdPedido(), "EM_PREPARACAO"));
        }
    }

    private void enviar(EstadoOSMudou estado) {
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("dr4_topic", estado);
        LOG.info("***** Mensagem Publicada ---> " + estado);
    }

    @ServiceActivator(inputChannel = "inputMessageChannel")
    private void receber(EstadoOSMudou payload,
                         @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) ConvertedBasicAcknowledgeablePubsubMessage<EstadoOSMudou> message) {

        LOG.info("***** Mensagem Recebida ---> " + payload);
        message.ack();
        this.processarEvento(payload);
    }

    public OrdemDeServico obterPorId(long id) {
        return repository.getReferenceById(id);
    }

    public OrdemDeServico concluir(long id) {
        OrdemDeServico ordemDeServico = this.obterPorId(id);
        ordemDeServico.concluirOrdem();
        ordemDeServico = repository.save(ordemDeServico);
        this.enviar(new EstadoOSMudou(ordemDeServico.getPedidoId(), "EM_TRANSITO"));
        return ordemDeServico;
    }
}
