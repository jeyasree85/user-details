package userdetails.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userdetails.exception.ResourceNotFoundException;
import userdetails.model.User;
import userdetails.repository.UserRepository;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //get users
    @GetMapping("users")
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
    //get user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
        throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()-> {
            return new ResourceNotFoundException("User not found for this id ::" + userId);
        });
        return ResponseEntity.ok().body(user);
    }


    //save user
    @PostMapping("users")
    public User createUser(@RequestBody User user){
        return this.userRepository.save(user);
    }
    //update user
    @PutMapping("users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value="id") Long userId, @Valid @RequestBody User userDetails) throws ResourceNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: "+userId));
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setDepartment(userDetails.getDepartment());
        user.setJobTitle(userDetails.getJobTitle());
        return ResponseEntity.ok(this.userRepository.save(user));


    }
    //delete user
    @DeleteMapping("users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found for this id :: "+userId));
        this.userRepository.delete(user);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }
}
