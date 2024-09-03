package com.project.board.repository;

import com.project.board.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteSiteUserRepository extends JpaRepository<SiteUser, Long>, SiteUserRepositoryCustom {
    Optional<SiteUser> findByUsername(String username);
}
