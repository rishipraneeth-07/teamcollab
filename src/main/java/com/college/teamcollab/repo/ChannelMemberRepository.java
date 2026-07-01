package com.college.teamcollab.repo;

import com.college.teamcollab.entity.Channel;
import com.college.teamcollab.entity.ChannelMember;
import com.college.teamcollab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelMemberRepository extends JpaRepository<ChannelMember,Long> {
    boolean existsByChannelAndUser(Channel channel, User user);
    Optional<ChannelMember> findByChannelAndUser(Channel channel, User user);
    List<ChannelMember> findByChannel(Channel channel);
    List<ChannelMember> findByUser(User user);
    void deleteByChannelAndUser(Channel channel, User user);
}
