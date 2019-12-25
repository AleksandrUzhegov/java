package com.ertelecom.server;

import com.ertelecom.gwt.common.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAll() {
        List<User> users = (List<User>)userRepository.findAll();
        List<UserDto> userDtos = new ArrayList();

        for (User t: users) {
            userDtos.add( t.getUserDto());
        }

        return userDtos;
    }

}
