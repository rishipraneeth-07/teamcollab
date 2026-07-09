package com.college.teamcollab.repo;

import com.college.teamcollab.entity.Channel;
import com.college.teamcollab.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByChannelOrderByCreatedAtAsc(Channel channel,Pageable pageable);

    }

