package com.mentorHub.api.repository;

import com.mentorHub.api.entity.RootKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RootKeywordRepository extends JpaRepository<RootKeywordEntity, Long> {

    @Query("""
    SELECT DISTINCT rk
    FROM RootKeywordEntity rk
    JOIN RootKeywordAliasEntity rka
      ON rka.rootKeyword = rk
    WHERE rka.aliasName IN :aliasName
    """)
    Optional<RootKeywordEntity> findByCanonicalName(@Param("aliasName") String aliasName);
}
