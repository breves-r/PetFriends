package br.edu.infnet.pedidos.infra.service;

import br.edu.infnet.pedidos.domain.Pedido;
import br.edu.infnet.pedidos.domain.PedidoStatus;
import br.edu.infnet.pedidos.eventos.EstadoPedidoMudou;
import br.edu.infnet.pedidos.infra.repository.PedidoRepository;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.google.cloud.spring.pubsub.support.converter.ConvertedBasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private static final Logger LOG = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PubSubTemplate pubSubTemplate;
    @Autowired
    JacksonPubSubMessageConverter converter;
    @Autowired
    private PedidoRepository repository;

    public Pedido obterPorId(long id) {
        return repository.getReferenceById(id);
    }

    public Pedido fecharPedido(long id) {
        Pedido pedido = repository.getReferenceById(id);
        pedido.fecharPedido();
        pedido = repository.save(pedido);
        enviar(new EstadoPedidoMudou(pedido.getId(), PedidoStatus.FECHADO));
        return pedido;
    }

    public Pedido criarPedido(Pedido pedido) {
        Pedido retorno = null;
        if (pedido != null) {
            retorno = repository.save(pedido);
            enviar(new EstadoPedidoMudou(pedido.getId(), PedidoStatus.NOVO));
        }
        return retorno;
    }

    public void processarEvento(EstadoPedidoMudou evento) {
        try {
            Pedido pedido = repository.findById(evento.getIdPedido()).orElseThrow();
            switch (evento.getEstado().toString()) {
                case "NOVO":
                    break;
                case "EM_PREPARACAO":
                    LOG.info("***** EM_PREPARACAO *****");
                    pedido.prepararPedido();
                    break;
                case "EM_TRANSITO":
                    LOG.info("***** EM_TRANSITO *****");
                    pedido.enviarPedido();
                    break;
                case "ENTREGUE":
                    LOG.info("***** ENTREGUE *****");
                    pedido.entregarPedido();
                    break;
            }

            repository.save(pedido);
        } catch (Exception e) {
            LOG.error("*** ERROR "+e.getMessage());
        }

    }

    private void enviar(EstadoPedidoMudou estado) {
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("dr4_topic", estado);
        LOG.info("***** Mensagem Publicada ---> " + estado);
    }

    @ServiceActivator(inputChannel = "inputMessageChannel")
    private void receber(EstadoPedidoMudou payload,
            @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) ConvertedBasicAcknowledgeablePubsubMessage<EstadoPedidoMudou> message) {

        LOG.info("***** Mensagem Recebida ---> " + payload);
        message.ack();
        this.processarEvento(payload);
    }
}
