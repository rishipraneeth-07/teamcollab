package com.college.teamcollab.repo;

import com.college.teamcollab.entity.Channel;
import com.college.teamcollab.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    boolean existsByName(String name);
    Optional<Channel> findByName(String name);
    Page<Channel> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
