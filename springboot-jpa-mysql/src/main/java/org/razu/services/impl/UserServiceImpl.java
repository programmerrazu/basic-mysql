package org.razu.services.impl;

import java.util.Optional;
import org.razu.entity.UserInfo;
import org.razu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.razu.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<UserInfo> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserInfo> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserInfo findUserByUserName(String uName) {
        return userRepository.findByUserName(uName);
    }

    @Override
    public UserInfo save(UserInfo user) {
        return userRepository.save(user);
    }

    @Override
    public UserInfo update(UserInfo user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(UserInfo user) {
        userRepository.delete(user);
        return !userRepository.findById(user.getId()).isPresent();
    }
}
