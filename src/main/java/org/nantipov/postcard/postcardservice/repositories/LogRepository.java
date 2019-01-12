package org.nantipov.postcard.postcardservice.repositories;

import org.nantipov.postcard.postcardservice.domain.LogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<LogEntity, Long> {

    List<LogEntity> findAllByOrderByCreatedAtDesc();

}
