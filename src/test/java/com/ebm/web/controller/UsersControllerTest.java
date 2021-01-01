package com.ebm.web.controller;


import com.ebm.domain.Users;
import com.ebm.domain.service.EBMUserDetailService;
import com.ebm.domain.service.UsersService;
import com.ebm.web.security.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UsersControllerTest {
    UsersController tester;
    UsersService usersService;
    EBMUserDetailService ebmUserDetailService;
    JWTUtil jwtUtil;
    AuthenticationManager authenticationManager;

    @BeforeEach()
    void initEach() {
        usersService = Mockito.mock(UsersService.class);
        ebmUserDetailService = Mockito.mock(EBMUserDetailService.class);
        jwtUtil = Mockito.mock(JWTUtil.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        tester = new UsersController(usersService, ebmUserDetailService, jwtUtil, authenticationManager);
    }

    @Test
    void TestSaveNull() {
        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), tester.signup(new Users()), "signup must be HttpStatus.CREATED");
    }

    @Test
    void TestSaveWithData() {
        Users users = new Users();
        when(usersService.save(users)).thenReturn(users);

        assertEquals(new ResponseEntity<>(users, HttpStatus.CREATED), tester.signup(users), "signup must be new instance Users");
    }

    @Test
    void TestGetUserByIdWithoutData() {
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), tester.getUserById(1), "getUserById must be []");
    }

    @Test
    void TestGetUserByIdWithData() {
        Optional<Users> users = Optional.of(new Users());

        when(usersService.getUserById(1)).thenReturn(users);

        assertEquals(new ResponseEntity<>(users.get(), HttpStatus.OK), tester.getUserById(1), "getUserById-1 must be new ResponseEntity with a value");
    }

}
