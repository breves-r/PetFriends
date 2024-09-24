package com.infnet.PetFriends_Almoxarifado.infra.repository;

import com.infnet.PetFriends_Almoxarifado.domain.StatusOrdemServico;
import jakarta.persistence.AttributeConverter;

public class OSStatusConverter implements AttributeConverter<StatusOrdemServico, String> {
    @Override
    public String convertToDatabaseColumn(StatusOrdemServico statusOrdemServico) {
        return statusOrdemServico.toString();
    }

    @Override
    public StatusOrdemServico convertToEntityAttribute(String s) {
        return StatusOrdemServico.valueOf(s);
    }
}
