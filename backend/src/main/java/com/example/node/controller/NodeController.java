package com.example.node.controller;
import com.example.node.entity.Node;
import com.example.node.service.NodeService;
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
    public ResponseEntity<Node> addNewNode(@RequestBody Node requestNode, @RequestParam Optional<Boolean> asParentForChildren) {
        //throw bad_request, when user is trying to add root, when tree already has a root
        if (requestNode.getParent_id() == null && service.getRootId().isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        if (requestNode.getParent_id() != null) {
            //if parent_id is given (new node is not a root) retrieve parent node
            Node parentNode = service.getNodeById(requestNode.getParent_id()).orElse(null);

            //if parent_id is not in the base throw bad_request
            if (parentNode == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            //set sum to node's value plus parent's sum and add it to the db
            requestNode.setSum(requestNode.getValue() + parentNode.getSum());
            Node newCreatedNode = service.addNewNode(requestNode);

            // if asParentForChildren was set to true
            // iterate through all parent's children, except for the new node and
            // change their parent to new node and recalculate sum

            if (asParentForChildren.isPresent() && asParentForChildren.get()) {
                for (Long childId : service.getNodeChildrenById(requestNode.getParent_id())) {
                    if (!Objects.equals(childId, newCreatedNode.getId())) {
                        service.updateNodeById(Optional.empty(),
                                childId,
                                Optional.of(newCreatedNode.getSum()),
                                Optional.of(newCreatedNode.getId()));
                    }
                }
            }
            return new ResponseEntity<>(newCreatedNode, HttpStatus.CREATED);
        } else {
            //parent_id was not given so it is a root, so sum = value
            requestNode.setSum(requestNode.getValue());
            Node newCreatedNode = service.addNewNode(requestNode);
            return new ResponseEntity<>(newCreatedNode, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Node> updateNode(@RequestBody Node newNode,
                                           @PathVariable Long id) {
        //check if id in the path and in the body are the same, otherwise throw bad_request
        if (!Objects.equals(newNode.getId(), id) ||
                service.getNodeById(newNode.getId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //take parent's sum, if parent_id is null -take 0

        Integer parentSum = newNode.getParent_id() != null ? service.getNodeById(newNode.getParent_id()).get().getSum() : 0;
        return new ResponseEntity<>(service.updateNodeById(Optional.of(newNode), id, Optional.of(parentSum), Optional.empty()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNodeById(@PathVariable Long id, @RequestParam Optional<Long> parentId) {
        Optional<Node> nodeToDelete = service.getNodeById(id);
        if (nodeToDelete.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //my idea is that we should not delete the root, because we could get more than one tree after destroying the links
        if (parentId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //for each child change their parent, to deleted node's parent
        //and recalculate sum, by omitting the value of deleted node
        service.getNodeChildrenById(id).forEach(childId -> service.updateNodeById(Optional.empty(),
                childId,
                Optional.of(nodeToDelete.get().getSum() - nodeToDelete.get().getValue()),
                parentId));


        service.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
