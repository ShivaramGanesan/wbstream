package com.wbstream.whiteboardstream.repo;

import com.wbstream.whiteboardstream.pojo.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepo extends JpaRepository<Board, Long> {
}
