package com.infnet.PetFriends_Transporte.infra.repository;

import com.infnet.PetFriends_Transporte.domain.StatusEntrega;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusEntregaConverter implements AttributeConverter<StatusEntrega, String> {

    @Override
    public String convertToDatabaseColumn(StatusEntrega entregaStatus) {
        return entregaStatus.toString();
    }

    @Override
    public StatusEntrega convertToEntityAttribute(String entregaStatus) {
        return StatusEntrega.valueOf(entregaStatus);
    }
}
