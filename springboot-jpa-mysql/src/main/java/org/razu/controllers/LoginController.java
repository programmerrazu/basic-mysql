package org.razu.controllers;

import java.util.LinkedHashMap;
import org.razu.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.razu.services.UserService;
import org.razu.utils.BcryptHashGenerator;
import org.razu.utils.MessgageUtils;
import org.razu.utils.ResponseTagName;
import static org.razu.utils.UrlUtils.LOGIN;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = LOGIN)
public class LoginController implements ResponseTagName, MessgageUtils {

    @Autowired
    private UserService userService;

    @PostMapping(path = LOGINS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> webUserLogin(@RequestBody UserInfo userInfo) {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
        UserInfo existUser = userService.findUserByUserName(userInfo.getUserName());
        if (existUser != null) {
            if (existUser.getUserStatus() == 1) {
                if (BcryptHashGenerator.checkPassword(userInfo.getUserPassword(), existUser.getUserPassword())) {
                    serviceResponse.put(STATUS, Boolean.TRUE);
                    serviceResponse.put(STATUS_CODE, CODE_200);
                    serviceResponse.put(MESSAGE, LOGIN_SUCC);
                    serviceResponse.put(USERS, existUser);
                    return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
                } else {
                    serviceResponse.put(STATUS, Boolean.TRUE);
                    serviceResponse.put(STATUS_CODE, CODE_201);
                    serviceResponse.put(MESSAGE, INVALID_UP);
                    return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
                }
            } else {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_201);
                serviceResponse.put(MESSAGE, USER_SUS);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            }
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, INVALID_UP);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }
}
