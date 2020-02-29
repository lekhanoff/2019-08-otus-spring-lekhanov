package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
