package com.mentorHub.api.repository;

import com.mentorHub.api.entity.RootKeywordAliasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RootKeywordAliasRepository extends JpaRepository<RootKeywordAliasEntity, Long> {
    List<RootKeywordAliasEntity> findAllByAliasNameIn(List<String> aliasNames);
}
