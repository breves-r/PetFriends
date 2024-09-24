package com.infnet.PetFriends_Transporte.infra.repository;

import com.infnet.PetFriends_Transporte.domain.Endereco;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EnderecoConverter implements AttributeConverter<Endereco, String> {

    @Override
    public String convertToDatabaseColumn(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return String.join(",", endereco.getRua(), endereco.getCidade(), endereco.getEstado(), endereco.getCep());
    }

    @Override
    public Endereco convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] data = dbData.split(",");
        return new Endereco(data[0], data[1], data[2], data[3]);
    }
}
