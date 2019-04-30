package org.razu.services;

import java.util.Optional;
import org.razu.entity.UserInfo;

public interface UserService {

    Iterable<UserInfo> findAllUser();

    Optional<UserInfo> findUserById(Long id);

    UserInfo findUserByUserName(String uName);

    UserInfo save(UserInfo user);

    UserInfo update(UserInfo user);

    Boolean delete(UserInfo user);

}
