package com.example.chess.domain.repository;

import com.example.chess.domain.service.appuser.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findAll();

}
