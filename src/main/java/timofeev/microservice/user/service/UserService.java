package timofeev.microservice.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import timofeev.microservice.user.exceptions.ResourceNotFoundException;
import timofeev.microservice.user.model.User;
import timofeev.microservice.user.repository.UserRepository;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(long id, User userToUpdate) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));

        user.setEmail(userToUpdate.getEmail());
        user.setLastName(userToUpdate.getLastName());
        user.setFirstName(userToUpdate.getFirstName());
        user.setUpdatedAt(new Date());

        return userRepository.save(user);
    }

    public void deleteUser(long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));
        userRepository.delete(user);
    }
}
