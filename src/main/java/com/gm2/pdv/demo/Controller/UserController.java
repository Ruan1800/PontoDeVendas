package com.gm2.pdv.demo.Controller;

import com.gm2.pdv.demo.Entity.User;
import com.gm2.pdv.demo.Repository.UserRepository;
import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

        @GetMapping()
        public ResponseEntity getAll() {
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
         }

         @PostMapping()
        public ResponseEntity post(@RequestBody  User user){
            try {
                user.setEnabled(true);
                return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
            }catch (Exception error){
                return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
         }

         @PutMapping()
        public ResponseEntity put(@RequestBody User user) {
          Optional<User> userToEdit = userRepository.findById(user.getId());
          if (userToEdit.isPresent()){
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
          }
          return ResponseEntity.notFound().build();
         }

         @DeleteMapping("{id}")
        public ResponseEntity delete(@PathVariable Long id) {
            try {

                userRepository.deleteById(id);
               return new ResponseEntity<>("Usuario removido com sucesso!", HttpStatus.OK);
            } catch (Exception error){
                return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }

}
}
