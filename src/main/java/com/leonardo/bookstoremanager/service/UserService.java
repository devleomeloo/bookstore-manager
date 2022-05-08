package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.dto.MessageDTO;
import com.leonardo.bookstoremanager.dto.UserDTO;
import com.leonardo.bookstoremanager.entitys.User;
import com.leonardo.bookstoremanager.exception.UserAlreadyExistsException;
import com.leonardo.bookstoremanager.mapper.UserMapper;
import com.leonardo.bookstoremanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.leonardo.bookstoremanager.utils.MessageDTOUtils.creationMessage;
import static com.leonardo.bookstoremanager.utils.MessageDTOUtils.updatedMessage;

@Service
public class UserService {

    private static final UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO){
        verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUserName());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);

        return creationMessage(createdUser);
    }

    public MessageDTO update(Long id, UserDTO userToUpdateDTO){
        User foundUser = verifyAndGetUser(id);
        userToUpdateDTO.setId(foundUser.getId());

        User userToUpdate = userMapper.toModel(userToUpdateDTO);
        userToUpdate.setCreatedDate(foundUser.getCreatedDate());

        User updatedUser = userRepository.save(userToUpdate);
        return updatedMessage(updatedUser);
    }

    public UserDTO findById(Long id){
        return userMapper.toDTO(verifyAndGetUser(id));
    }

    public List<UserDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public void delete(Long id){
        verifyAndGetUser(id);
        userRepository.deleteById(id);
    }

    private User verifyAndGetUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void verifyIfExists(String userEmail, String userName) {
        Optional<User> foundUser = userRepository.findByEmailOrUserName(userEmail, userName);
        if (foundUser.isPresent()){
            throw new UserAlreadyExistsException(userEmail, userName);
        }
    }
}
