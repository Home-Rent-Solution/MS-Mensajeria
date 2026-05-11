package com.homerentsolution.msmensajeria.repository;

import com.homerentsolution.msmensajeria.model.Mensajeria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeriaRepository extends JpaRepository<Mensajeria, Long> {

    List<Mensajeria> findByIdEmisorOrderByFechaDesc(Long idEmisor);//ordenar mensajes del emisor por orden desc

    List<Mensajeria> findByIdReceptor(Long idReceptor);

    List<Mensajeria> findByIdEmisorAndIdReceptor(Long idEmisor,
                                                 Long idReceptor);
}
