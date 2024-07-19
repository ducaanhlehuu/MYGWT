package com.hello.controller;

import com.hello.server.ObjectifyUserService;
import com.hello.shared.model.ResponseDTO;
import com.hello.shared.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ObjectifyUserService observice;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getContact() {
        try {
        	List<User> users = observice.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDTO> deleteContact(@PathVariable Long userId) {
        User userInDB = observice.getUserById(userId);
        if (userInDB != null) {
            observice.deleteUser(userInDB);
            return new ResponseEntity<>(new ResponseDTO("200", "User deleted successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO("404", "User not found"), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<User> updateContact(@RequestBody User userDAO) {
        User userInDB = observice.getUserByUsername(userDAO.getUsername());
        if (userInDB != null) {
            userInDB.setAddress(userDAO.getAddress());
            userInDB.setPhoneNumber(userDAO.getPhoneNumber());
            userInDB.setName(userDAO.getName());
            User updatedUser = observice.updateUser(userInDB);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            User newUser = observice.addUser(userDAO);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/checkExist", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> checkExist(@RequestBody User userDAO) {
        User userInDB = observice.getUserByUsername(userDAO.getUsername());
        boolean exists = userInDB != null;
        if (exists) {
            return new ResponseEntity<>(new ResponseDTO("200", "User exists"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO("404", "User does not exist"), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<User>> findContact(@RequestParam(value = "property", required = false) String property,
                                                  @RequestParam(value = "keyword", required = false) String keyword) {
        if (property == null || keyword == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<User> users;
        if (property.equals("all")) {
            users = observice.searchUser(keyword);
        } else {
            users = observice.findUsers(property, keyword);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
