package com.infnet.PetFriends_Almoxarifado.controllers;

import com.infnet.PetFriends_Almoxarifado.domain.OrdemDeServico;
import com.infnet.PetFriends_Almoxarifado.infra.service.OrdemDeServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class OrdemDeServicoController {

    @Autowired
    private OrdemDeServicoService service;

    @GetMapping("/{id}")
    public OrdemDeServico obterPorId(@PathVariable(value = "id") long id){
        return service.obterPorId(id);
    }

    @PatchMapping("/concluir/{id}")
    public OrdemDeServico concluirOS(@PathVariable(value = "id") long id){
        return service.concluir(id);
    }
}
