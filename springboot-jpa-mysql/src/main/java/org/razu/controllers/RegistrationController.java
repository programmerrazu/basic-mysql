package org.razu.controllers;

import java.util.LinkedHashMap;
import javax.validation.Valid;
import org.razu.entity.UserInfo;
import org.razu.services.UserService;
import org.razu.utils.DateFormatProvider;
import org.razu.utils.ErrorUtils;
import org.razu.utils.MessgageUtils;
import org.razu.utils.ResponseTagName;
import static org.razu.utils.UrlUtils.REGISTRATION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = REGISTRATION)
public class RegistrationController implements ResponseTagName, MessgageUtils {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping(path = REGISTRATIONS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> webUserCreate(@Valid @RequestBody UserInfo userInfo, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(ErrorUtils.userError(result), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
            UserInfo existUser = userService.findUserByUserName(userInfo.getUserName());
            if (existUser == null) {
                userInfo.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
                userInfo.setUserStatus(1);
                userInfo.setInsertDate(DateFormatProvider.now());
                UserInfo regUser = userService.save(userInfo);
                if (regUser != null) {
                    serviceResponse.put(STATUS, Boolean.TRUE);
                    serviceResponse.put(STATUS_CODE, CODE_200);
                    serviceResponse.put(MESSAGE, REG_SUCC);
                    serviceResponse.put(USERS, regUser);
                    return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
                } else {
                    serviceResponse.put(STATUS, Boolean.TRUE);
                    serviceResponse.put(STATUS_CODE, CODE_201);
                    serviceResponse.put(MESSAGE, REG_FAILED);
                    return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
                }
            } else {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_201);
                serviceResponse.put(MESSAGE, UN_NOT_AVAIL);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            }
        }
    }
}
