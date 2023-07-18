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
    public ResponseEntity<Object> addNewNode(@RequestBody Node newRequestNode, @RequestParam("childId") Optional<Long> childId, @RequestParam("parentSum") Optional<Integer> parentSum) {

        newRequestNode.setSum(parentSum.orElse(0) + newRequestNode.getValue());
        Node createdNewNode = service.addNewNode(newRequestNode);

        if (childId.isPresent()) {
            Optional<Node> childNode = service.getNodeById(childId.get());
            if (childNode.isPresent()) {
                service.updateNodeById(null, childId.get(), Optional.empty(), Optional.of(createdNewNode.getId()));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(createdNewNode, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNode(@RequestBody Node newNode, @PathVariable Long id, @RequestParam Optional<Integer> parent_sum) {
        if (!Objects.equals(newNode.getId(), id) ||
                service.getNodeById(newNode.getId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.updateNodeById(newNode, id, parent_sum, Optional.empty()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNodeById(@PathVariable Long id) {
        if (service.getNodeById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
