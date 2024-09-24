package com.infnet.PetFriends_Transporte.infra.message;

import com.infnet.PetFriends_Transporte.eventos.EstadoEntregaMudou;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class EstadoEntregaMudouSerializer extends StdSerializer<EstadoEntregaMudou> {

    public EstadoEntregaMudouSerializer() {
        super(EstadoEntregaMudou.class);
    }

    @Override
    public void serialize(EstadoEntregaMudou evento, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("idPedido", evento.getIdPedido());
        jgen.writeStringField("estado", evento.getEstado());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        String data = sdf.format(evento.getMomento());
        jgen.writeStringField("momento", data);
    }
}
