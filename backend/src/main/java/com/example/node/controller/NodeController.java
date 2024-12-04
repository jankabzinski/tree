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
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(nodes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Node> getNode(@PathVariable Long id) {
        try {
            Optional<Node> node = service.getNodeById(id);
            return node.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Node> addNewNode(@RequestBody Node requestNode, @RequestParam Optional<Boolean> asParentForChildren) {
        try{
            //throw bad_request, when user is trying to add root, when tree already has a root
            if(requestNode.getParent_id() == null && service.getRootId().isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }


            if (requestNode.getParent_id() != null) {
                //if parent_id is given (new node is not a root) retrieve parent node
                Node parentNode = service.getNodeById(requestNode.getParent_id()).orElse(null);

                //if parent_id is not in the base throw bad_request
                if (parentNode == null)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

                //set sum to node's value plus parent's sum and add it to the db
                requestNode.setSum(requestNode.getValue() + parentNode.getSum());
                Node newNode = service.addNewNode(requestNode);

                // asParentForChildren was not implemented on fronted
                // the idea is that instead of adding new node as leaf, we insert
                // the node between parent node and its children
                // before insert: A -> {B,C} after insert : A-> D -> {B,C}

                // if asParentForChildren was set to true
                // iterate through all parent's children, except for the new node and
                // change their parent to new node and recalculate sum

                if (asParentForChildren.isPresent() && asParentForChildren.get()) {
                    for (Long childId : service.getNodeChildrenById(requestNode.getParent_id())) {
                        if (!Objects.equals(childId, newNode.getId())) {
                            service.updateNodeById(Optional.empty(),
                                    childId,
                                    Optional.of(newNode.getSum()),
                                    Optional.of(newNode.getId()));
                        }
                    }
                }
                return ResponseEntity.status(HttpStatus.CREATED).body(newNode);
            } else {
                // if parent_id was not given then it is a root, so sum = value
                requestNode.setSum(requestNode.getValue());
                Node newNode = service.addNewNode(requestNode);
                return ResponseEntity.status(HttpStatus.CREATED).body(newNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Node> updateNode(@RequestBody Node updatedNode,
                                           @PathVariable Long id) {
        try {
            if (!Objects.equals(updatedNode.getId(), id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Optional<Node> existingNodeOpt = service.getNodeById(id);
            if (existingNodeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            int parentSum = updatedNode.getParent_id() != null
                    ? service.getNodeById(updatedNode.getParent_id()).map(Node::getSum).orElse(0)
                    : 0;

            Node updated = service.updateNodeById(Optional.of(updatedNode), id, Optional.of(parentSum), Optional.empty());
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNodeById(@PathVariable Long id, @RequestParam Optional<Long> parentId) {
        try {
            Optional<Node> nodeOpt = service.getNodeById(id);
            if (nodeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            // for now, removing root is impossible
            if (parentId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            // reparent each 'orphan'
            Node nodeToDelete = nodeOpt.get();
            service.getNodeChildrenById(id).forEach(childId -> service.updateNodeById(Optional.empty(),
                    childId,
                    Optional.of(nodeToDelete.getSum() - nodeToDelete.getValue()),
                    parentId));

            service.deleteEmployeeById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
