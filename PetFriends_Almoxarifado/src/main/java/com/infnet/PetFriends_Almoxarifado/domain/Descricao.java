package com.infnet.PetFriends_Almoxarifado.domain;

import java.util.Objects;

public class Descricao {
    private final String valor;

    public Descricao(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser nula ou vazia.");
        }
        if (valor.length() > 255) {
            throw new IllegalArgumentException("A descrição não pode exceder 255 caracteres.");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        final Descricao outro = (Descricao) o;
        return Objects.equals(this.valor, outro.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
