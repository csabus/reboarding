package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.excpetion.UserNotFoundException;
import hu.progmasters.reboarding.models.User;
import hu.progmasters.reboarding.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping()
    @RequestMapping("{id}")
    public User get(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody final User user) {
        return userRepository.saveAndFlush(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public User update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            BeanUtils.copyProperties(user, existingUser.get(), "userId");
            return userRepository.saveAndFlush(existingUser.get());
        } else {
            throw new UserNotFoundException(id);
        }
    }

}
