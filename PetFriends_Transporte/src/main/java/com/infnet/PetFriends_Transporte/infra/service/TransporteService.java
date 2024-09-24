package com.infnet.PetFriends_Transporte.infra.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.google.cloud.spring.pubsub.support.converter.ConvertedBasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.infnet.PetFriends_Transporte.domain.Endereco;
import com.infnet.PetFriends_Transporte.domain.Entrega;
import com.infnet.PetFriends_Transporte.domain.ManifestoDeTransporte;
import com.infnet.PetFriends_Transporte.eventos.EstadoEntregaMudou;
import com.infnet.PetFriends_Transporte.infra.client.Pedido;
import com.infnet.PetFriends_Transporte.infra.repository.EntregaRepository;
import com.infnet.PetFriends_Transporte.infra.repository.ManifestoDeTransporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TransporteService {
    private static final Logger LOG = LoggerFactory.getLogger(TransporteService.class);
    @Autowired
    private PubSubTemplate pubSubTemplate;
    @Autowired
    JacksonPubSubMessageConverter converter;
    @Autowired
    private ManifestoDeTransporteRepository repository;
    @Autowired
    private EntregaRepository entregaRepository;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ClienteService clienteService;

    private void criarManifesto(Pedido pedido) {
        ManifestoDeTransporte manifesto;
        Optional<ManifestoDeTransporte> manifestoDb = this.obterManifestosEmEdicao();

        if (manifestoDb.isPresent()) {
            manifesto = manifestoDb.get();
        } else {
            manifesto = getManifestoDetails();
        }

        manifesto.adicionarEntrega(pedido.getID(), clienteService.getEnderecoByCustomerId(pedido.getCustomerId()));
        repository.save(manifesto);
    }

    public void processarEvento(EstadoEntregaMudou evento) {
        if(Objects.equals(evento.getEstado(), "EM_TRANSITO")) {
            Pedido pedido = pedidoService.getPedidoById(evento.getIdPedido());
            this.criarManifesto(pedido);
        }
    }

    private void enviar(EstadoEntregaMudou estado) {
        pubSubTemplate.setMessageConverter(converter);
        pubSubTemplate.publish("dr4_topic", estado);
        LOG.info("***** Mensagem Publicada ---> " + estado);
    }

    @ServiceActivator(inputChannel = "inputMessageChannel")
    private void receber(EstadoEntregaMudou payload,
                         @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) ConvertedBasicAcknowledgeablePubsubMessage<EstadoEntregaMudou> message) {

        LOG.info("***** Mensagem Recebida ---> " + payload);
        message.ack();
        this.processarEvento(payload);
    }

    public Entrega concluirEntrega(long entregaId) {
        Entrega entrega = entregaRepository.getReferenceById(entregaId);
        entrega.concluirEntrega();
        entrega = entregaRepository.save(entrega);
        this.enviar(new EstadoEntregaMudou(entrega.getPedidoId(), "ENTREGUE"));
        return entrega;
    }

    public ManifestoDeTransporte obterPorId(long id) {
        return repository.getReferenceById(id);
    }

    public ManifestoDeTransporte concluirEdicao(long id) {
        ManifestoDeTransporte manifesto = this.obterPorId(id);
        manifesto.setEmEdicao(false);
        return repository.save(manifesto);
    }

    public Optional<ManifestoDeTransporte> obterManifestosEmEdicao() {
        return repository.findEmEdicao();
    }

    private ManifestoDeTransporte getManifestoDetails() {
        Endereco origem = new Endereco("Filial RJ", "Rio de Janeiro", "RJ", "20041-005");
        Endereco destino = new Endereco("Filial SP", "São Paulo", "SP", "04756-050");
        return new ManifestoDeTransporte("Motorista Fulano", "XXXXXX", origem, destino);
    }
}
