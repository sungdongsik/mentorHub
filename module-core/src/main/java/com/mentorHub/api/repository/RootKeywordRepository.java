package com.mentorHub.api.repository;

import com.mentorHub.api.entity.RootKeywordEntity;
import com.util.RootKeywordAliasStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    List<RootKeywordEntity> findAllByStatus(RootKeywordAliasStatus status);
}
