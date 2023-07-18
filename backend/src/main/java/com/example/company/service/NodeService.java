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

    public Node updateNodeById(Node newNode, Long id) {
        return repository.findById(id).map(node -> {
            node.setValue(newNode.getValue());
            node.setParent_id(newNode.getParent_id());
            node.setSum(newNode.getSum());
            return repository.save(node);
        }).orElseGet(() -> repository.save(newNode));
    }

    public Node updateSumInNodeById(int parent_sum, Long id) {
        return repository.findById(id)
                .map(node -> {
                    node.setSum(parent_sum + node.getValue());
                    return repository.save(node);
                })
                .orElseThrow(() -> new NoSuchElementException("Węzeł o podanym identyfikatorze nie istnieje"));
    }

    public void deleteEmployeeById(Long id) {
        repository.deleteById(id);
    }
}
