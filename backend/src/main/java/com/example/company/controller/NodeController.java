package com.example.company.controller;

import com.example.company.entity.Node;
import com.example.company.service.NodeService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/nodes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class NodeController {

    private final NodeService service;

    public NodeController(NodeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAllNodes() {
        try {
            List<Node> nodes = this.service.getAllNodes();

            if (nodes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(nodes, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNode(@PathVariable Long id) {
        var result = service.getNodeById(id);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Object> addNewNode(@RequestBody Node newNode, @RequestParam("parentNode") Optional<Node> parentNode) {
        if (newNode.getId() != null && service.getNodeById(newNode.getId()).isPresent() ||
        newNode.getParent_id() == null && service.getRootId().isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (parentNode.isPresent()) {
            Node presentParentNode = parentNode.get();
            if (service.getNodeById(presentParentNode.getId()).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            newNode.setSum(presentParentNode.getSum() + newNode.getValue());
            for (Long childId : service.getNodeChildrenById(presentParentNode.getId())) {
                service.updateNodeById(null,
                        childId,
                        Optional.of(newNode.getSum()),
                        Optional.of(newNode.getId()));
            }
        } else {
            newNode.setSum(newNode.getValue());
        }

        return new ResponseEntity<>(service.addNewNode(newNode), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNode(@RequestBody Node newNode,
                                             @PathVariable Long id,
                                             @RequestParam Optional<Integer> parent_sum) {
        if (!Objects.equals(newNode.getId(), id) ||
                service.getNodeById(newNode.getId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.updateNodeById(newNode, id, parent_sum, Optional.empty()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNodeById(@PathVariable Long id, @RequestParam Optional<Long> parentId) {
        Optional<Node> nodeToDelete = service.getNodeById(id);
        if (nodeToDelete.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (parentId.isPresent()) {
            service.getNodeChildrenById(id).forEach(childId -> service.updateNodeById(null,
                    childId,
                    Optional.of(nodeToDelete.get().getSum() - nodeToDelete.get().getValue()),
                    parentId));
        }
        service.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
