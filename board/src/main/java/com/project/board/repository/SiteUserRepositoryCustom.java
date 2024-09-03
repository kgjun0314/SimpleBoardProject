package com.project.board.repository;

import com.project.board.entity.SiteUserResponseDto;

import java.util.Optional;

public interface SiteUserRepositoryCustom {
    Optional<SiteUserResponseDto> findDtoByUsername(String username);
}
