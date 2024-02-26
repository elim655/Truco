package com.backend.backend.repository;

import com.backend.backend.model.Invitation;
import com.backend.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {


    void deleteByReceiver(User receiver);

    Optional<Invitation> findByUserNameAndRoomId(String receiver, Long roomId);

    List<Invitation> findByReceiver(User receiver);

    List<Invitation> findByuserName(String receiverName);
}

