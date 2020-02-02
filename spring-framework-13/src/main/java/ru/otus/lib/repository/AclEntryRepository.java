package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.AclEntry;

@Repository
public interface AclEntryRepository extends JpaRepository<AclEntry, Long>{
}
