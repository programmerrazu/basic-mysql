package org.razu.repository;

import org.razu.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUserName(String userName);

}
