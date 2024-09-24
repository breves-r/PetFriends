package com.infnet.PetFriends_Transporte.infra.message;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.infnet.PetFriends_Transporte.eventos.EstadoEntregaMudou;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EstadoEntregaMudouDeserializer extends StdDeserializer<EstadoEntregaMudou> {
    
    public EstadoEntregaMudouDeserializer() {
        super(EstadoEntregaMudou.class);
    }

    @Override
    public EstadoEntregaMudou deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
        EstadoEntregaMudou evento = null;
        JsonNode node = jp.getCodec().readTree(jp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        try {
            evento = new EstadoEntregaMudou(
                    node.get("idPedido").asLong(),
                    node.get("estado").asText(),
                    sdf.parse(node.get("momento").asText())
            );
        } catch (ParseException e) {
            throw new IOException("Erro na data");
        }
        return evento;
    }
}
