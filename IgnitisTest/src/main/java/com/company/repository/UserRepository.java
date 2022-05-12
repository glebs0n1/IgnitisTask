package com.company.repository;

import com.company.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    User getByEmail(String email);

    @EntityGraph(attributePaths = {"posts"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.id=?1")
    User getWithPosts(int id);
}
