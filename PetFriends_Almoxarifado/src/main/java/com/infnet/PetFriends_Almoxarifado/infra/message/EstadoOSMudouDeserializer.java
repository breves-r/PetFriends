package com.infnet.PetFriends_Almoxarifado.infra.message;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.infnet.PetFriends_Almoxarifado.eventos.EstadoOSMudou;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EstadoOSMudouDeserializer extends StdDeserializer<EstadoOSMudou> {
    
    public EstadoOSMudouDeserializer() {
        super(EstadoOSMudou.class);
    }

    @Override
    public EstadoOSMudou deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JacksonException {
        EstadoOSMudou evento = null;
        JsonNode node = jp.getCodec().readTree(jp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        try {
            evento = new EstadoOSMudou(
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
