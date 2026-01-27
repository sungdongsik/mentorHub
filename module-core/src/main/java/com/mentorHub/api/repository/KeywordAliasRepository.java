package com.mentorHub.api.repository;

import com.mentorHub.api.entity.KeywordAliasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordAliasRepository extends JpaRepository<KeywordAliasEntity, Long> {
    List<KeywordAliasEntity> findAllByAliasNameIn(List<String> aliasNames);
}
