package com.infnet.PetFriends_Transporte.infra.repository;

import com.infnet.PetFriends_Transporte.domain.ManifestoDeTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ManifestoDeTransporteRepository extends JpaRepository<ManifestoDeTransporte, Long> {
    @Query("SELECT m FROM ManifestoDeTransporte m WHERE m.emEdicao = true")
    Optional<ManifestoDeTransporte> findEmEdicao();
}
