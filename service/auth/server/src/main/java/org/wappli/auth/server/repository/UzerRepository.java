package org.wappli.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.wappli.auth.server.domain.Uzer;

import java.time.Instant;

@SuppressWarnings("unused")
@Repository
public interface UzerRepository extends JpaRepository<Uzer, Long>,
        JpaSpecificationExecutor<Uzer> {

    Uzer findByActivationHashAndActivateDeadlineGreaterThan(String activationHash, Instant currentTime);

    @Query(value = "select now()", nativeQuery = true)
    Instant currentTime();

    Uzer findByEmail(String email);
}
