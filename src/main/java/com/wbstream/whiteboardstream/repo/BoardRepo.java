package com.wbstream.whiteboardstream.repo;

import com.wbstream.whiteboardstream.pojo.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {
}
