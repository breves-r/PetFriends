package com.infnet.PetFriends_Almoxarifado.infra.repository;

import com.infnet.PetFriends_Almoxarifado.domain.Descricao;
import jakarta.persistence.AttributeConverter;

public class DescricaoConverter implements AttributeConverter<Descricao, String> {
    @Override
    public String convertToDatabaseColumn(Descricao descricao) {
        return descricao.getValor();
    }

    @Override
    public Descricao convertToEntityAttribute(String s) {
        return new Descricao(s);
    }
}
