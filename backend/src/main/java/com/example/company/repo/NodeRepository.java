package com.example.company.repo;

import com.example.company.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    @Query("SELECT n.id FROM Node n WHERE n.parent_id = :parentId")
    List<Long> findIdsByParentId(Long parentId);
}
