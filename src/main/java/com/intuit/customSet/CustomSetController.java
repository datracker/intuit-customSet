package com.intuit.customSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/set")
public class CustomSetController {

    private final CustomSet customSet;
//    private final RegularSet regularSet;


    @Autowired
    public CustomSetController() {
//        this.regularSet = new RegularSet();
        this.customSet = new CustomSet();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody Integer item) {
        try {
            boolean res = customSet.add(item);
            if (res) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Item added successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.OK).body("Item already exists");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/has")
    public ResponseEntity<Boolean> hasItem(@RequestParam Integer item) {
        try {
            boolean exists = customSet.contains(item);
            return ResponseEntity.ok(exists);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeItem(@RequestBody Integer item) {
        try {
            boolean res = customSet.contains(item);customSet.remove(item);
            if (res) {
                return ResponseEntity.status(HttpStatus.OK).body("Item removed successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item does not exist");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


//    @PostMapping("/addReg")
//    public ResponseEntity<String> addItemReg(@RequestBody Integer item) {
//        try {
//            regularSet.add(item);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Item added successfully");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/hasReg")
//    public ResponseEntity<Boolean> hasItemReg(@RequestParam Integer item) {
//        try {
//            boolean exists = regularSet.contains(item);
//            return ResponseEntity.ok(exists);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
//        }
//    }

    @GetMapping("/test")
    public ResponseEntity<String> isAlive() {
        return ResponseEntity.ok("Alive");
    }
}
