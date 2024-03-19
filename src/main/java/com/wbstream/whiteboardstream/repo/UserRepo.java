package com.wbstream.whiteboardstream.repo;

import com.wbstream.whiteboardstream.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
