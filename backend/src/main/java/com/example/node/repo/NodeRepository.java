package com.example.node.repo;

import com.example.node.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    @Query("SELECT n.id FROM Node n WHERE n.parent_id = :parentId")
    List<Long> findIdsByParentId(Long parentId);

    @Query("select n.id FROM Node n where n.parent_id is null")
    Optional<Long> findRootId();
}
