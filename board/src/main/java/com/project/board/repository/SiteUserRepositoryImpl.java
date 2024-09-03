package com.project.board.repository;

import com.project.board.entity.QSiteUserResponseDto;
import com.project.board.entity.SiteUserResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.project.board.entity.QSiteUser.siteUser;

public class SiteUserRepositoryImpl implements SiteUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public SiteUserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<SiteUserResponseDto> findDtoByUsername(String username) {
        return Optional.ofNullable(queryFactory
                .select(new QSiteUserResponseDto(
                        siteUser.username,
                        siteUser.password,
                        siteUser.email
                ))
                .from(siteUser)
                .where(siteUser.username.eq(username))
                .fetchOne());
    }
}
