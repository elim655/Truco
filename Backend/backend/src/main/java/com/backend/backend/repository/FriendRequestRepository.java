package com.backend.backend.repository;

import com.backend.backend.model.FriendRequest;
import com.backend.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    void deleteBySenderOrReceiver(User userToDelete, User userToDelete1);
}

