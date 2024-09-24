package com.infnet.PetFriends_Transporte.infra.service;

import com.infnet.PetFriends_Transporte.domain.Endereco;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    public Endereco getEnderecoByCustomerId(Long customerId) {
        return new Endereco("R. São José, 90", "Rio de Janeiro", "RJ", "20010-020");
    }
}
