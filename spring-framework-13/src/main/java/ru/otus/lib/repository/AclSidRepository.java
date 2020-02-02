package ru.otus.lib.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.AclSid;

@Repository
public interface AclSidRepository extends JpaRepository<AclSid, Long>{
    
    Optional<AclSid> findBySid(String sid);
}
