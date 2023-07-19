package com.example.node.service;

import com.example.node.entity.Node;
import com.example.node.repo.NodeRepository;

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

    public Node updateNodeById(Optional <Node> newNode, Long id, Optional<Integer> parentSum, Optional<Long> newParentId) {
        //if Node object was given, map its value and parent_id, otherwise take parent_id from the argument newParentId (it should be given in that case)
        return repository.findById(id).map(node -> {
            if (newNode.isPresent()) {
                node.setValue(newNode.get().getValue());
                node.setParent_id(newNode.get().getParent_id());
            } else newParentId.ifPresent(node::setParent_id);

            //set sum to parent's sum + node's value
            node.setSum(parentSum.orElse(0) + node.getValue());

            //for each child of our node update theirs sum
            getNodeChildrenById(id).forEach(childId -> updateNodeById(Optional.empty(), childId, Optional.of(node.getSum()), Optional.empty()));


            return repository.save(node);
        }).orElseThrow(() -> new NoSuchElementException("Węzeł o podanym identyfikatorze nie istnieje"));
        //throw Exception when node with given id was not found
    }


    public List<Long> getNodeChildrenById(Long id) {
        return repository.findIdsByParentId(id);
    }

    public void deleteEmployeeById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Long> getRootId() {
        return repository.findRootId();
    }
}
