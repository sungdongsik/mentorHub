package com.mentorHub.api.repository;

import com.mentorHub.api.entity.RootKeywordEntity;
import com.util.RootKeywordAliasStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RootKeywordRepository extends JpaRepository<RootKeywordEntity, Long> {

    List<RootKeywordEntity> findAllByStatus(RootKeywordAliasStatus status);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE RootKeywordEntity rk SET rk.status = :newStatus WHERE rk.status = :oldStatus")
    int bulkUpdateStatus(@Param("oldStatus") RootKeywordAliasStatus oldStatus, @Param("newStatus") RootKeywordAliasStatus newStatus);

    @Query("""
    SELECT DISTINCT rk
    FROM RootKeywordEntity rk
    JOIN RootKeywordAliasEntity rka ON rka.rootKeyword = rk
    WHERE rka.aliasName IN :aliasNames
    """)
    List<RootKeywordEntity> findCanonicalName(@Param("aliasNames") List<String> aliasNames);
}