package com.wbstream.whiteboardstream.repo;

import com.wbstream.whiteboardstream.pojo.BoardUsers;
import com.wbstream.whiteboardstream.pojo.User;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BoardUsersRepo extends JpaRepository<BoardUsers, Long> {

    public List<BoardUsers> findAllByBoardId(Long boardId);

    @Modifying
    @Transactional
    @Query(value = "delete from boardusers where board_id = :boardId AND user_id in :userIds", nativeQuery = true)
    public void deleteUsersFromBoard(Long boardId, List<Long> userIds);

    @Modifying
    @Query(value = "delete from boardusers where board_id = :boardId", nativeQuery = true)
    public void deleteAllUsersFromBoard(Long boardId);

    @Modifying
    @Query(value = "SELECT new com.wbstream.whiteboardstream.pojo.User(u.name, u.id) FROM BoardUsers bu JOIN User u on u.id = bu.userId WHERE bu.boardId = :boardId")

//    @Query(value = "select u.id, u.name from boardusers bu join wbuser u on bu.user_id = u.id and bu.board_id = :boardId", nativeQuery = true)

    public <T> List<T> findUsersByBoardId(Long boardId);

}
