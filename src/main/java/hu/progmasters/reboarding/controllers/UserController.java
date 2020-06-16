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
    @RequestMapping("{userId}")
    public User get(@PathVariable Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody final User user) {
        return userRepository.saveAndFlush(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.PUT)
    public User update(@PathVariable Long userId, @RequestBody User user) {
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isPresent()) {
            BeanUtils.copyProperties(user, existingUser.get(), "userId");
            return userRepository.saveAndFlush(existingUser.get());
        } else {
            throw new UserNotFoundException(userId);
        }
    }

}
