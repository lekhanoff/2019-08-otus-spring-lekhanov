package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

}
