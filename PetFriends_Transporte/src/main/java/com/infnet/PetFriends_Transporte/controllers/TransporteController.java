package com.infnet.PetFriends_Transporte.controllers;

import com.infnet.PetFriends_Transporte.domain.Entrega;
import com.infnet.PetFriends_Transporte.domain.ManifestoDeTransporte;
import com.infnet.PetFriends_Transporte.infra.service.TransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TransporteController {
    @Autowired
    private TransporteService service;
    @GetMapping("/manifestos/{id}")
    public ManifestoDeTransporte obterManifestoPorId(@PathVariable long id) {
        return service.obterPorId(id);
    }

    @PatchMapping("/manifestos/concluir-edicao/{id}")
    public ManifestoDeTransporte concluirEdicao(@PathVariable long id) {
        return service.concluirEdicao(id);
    }

    @PostMapping("/entregas/concluir/{id}")
    public Entrega concluirEntrega(@PathVariable long id) {
        return service.concluirEntrega(id);
    }

}
