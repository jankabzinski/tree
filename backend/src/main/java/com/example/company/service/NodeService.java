package com.example.company.service;

import com.example.company.entity.Node;
import com.example.company.repo.NodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NodeService {
    private final NodeRepository repository;

    @Autowired
    public NodeService(NodeRepository repository) {
        this.repository = repository;
    }

    public List<Node> getAllNodes() {
        return repository.findAll();
    }

    public Optional<Node> getNodeById(long id) {
        return repository.findById(id);
    }

    public Node addNewNode(Node newNode) {
        return repository.save(newNode);
    }

    public Node updateNodeById(Node newNode, Long id, Optional<Integer> parentSum, Optional<Long> newParentId) {
        return repository.findById(id).map(node -> {
            if (newNode != null) {
                node.setValue(newNode.getValue());
                node.setParent_id(newNode.getParent_id());
            } else newParentId.ifPresent(node::setParent_id);

            node.setSum(parentSum.orElse(0) + node.getValue());


            List<Long> childrenIds = getNodeChildrenById(id);
            childrenIds.forEach(childId -> updateNodeById(null, childId, Optional.of(node.getSum()), Optional.empty()));


            return repository.save(node);
        }).orElseThrow(() -> new NoSuchElementException("Węzeł o podanym identyfikatorze nie istnieje"));
    }


    private List<Long> getNodeChildrenById(Long id) {
        return repository.findIdsByParentId(id);
    }

    public void deleteEmployeeById(Long id) {
        repository.deleteById(id);
    }
}
