package com.infnet.PetFriends_Transporte.domain;

import java.util.Objects;

public class Endereco {
    private final String rua;
    private final String cidade;
    private final String estado;
    private final String cep;

    public Endereco(String rua, String cidade, String estado, String cep) {
        if (rua == null || cidade == null || estado == null || cep == null) {
            throw new IllegalArgumentException("Os campos do endereço não podem ser nulos.");
        }
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(rua, endereco.rua) &&
                Objects.equals(cidade, endereco.cidade) &&
                Objects.equals(estado, endereco.estado) &&
                Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rua, cidade, estado, cep);
    }
}
