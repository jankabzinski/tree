package com.example.company.controller;

import com.example.company.entity.Node;
import com.example.company.service.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class NodeController {

    private final NodeService service;
    private final RequestCounter requestCounter;

    public NodeController(NodeService service, RequestCounter requestCounter) {
        this.service = service;
        this.requestCounter = requestCounter;
    }

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        try {
            requestCounter.incrementCount();
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
    public ResponseEntity<Object> getEmployee(@PathVariable Long id) {
        requestCounter.incrementCount();
        var result = service.getNodeById(id);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Object> addNewEmployee(@RequestBody Node newNode) {
        requestCounter.incrementCount();
        if (service.getNodeById(newNode.getId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.addNewNode(newNode), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> replaceEmployee(@RequestBody Node newNode, @PathVariable Long id) {
        requestCounter.incrementCount();
        if (!Objects.equals(newNode.getId(), id) ||
                service.getNodeById(newNode.getId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.replaceNodeById(newNode, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable Long id) {
        requestCounter.incrementCount();
        if (service.getNodeById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
