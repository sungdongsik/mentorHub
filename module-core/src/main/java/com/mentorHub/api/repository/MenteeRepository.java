package com.mentorHub.api.repository;

import com.mentorHub.api.entity.MenteeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MenteeRepository extends JpaRepository<MenteeEntity, Long> {

}
