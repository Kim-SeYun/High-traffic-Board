package com.board.boardserver.controller;

import com.board.boardserver.aop.LoginCheck;
import com.board.boardserver.dto.UserDTO;
import com.board.boardserver.dto.request.UserDeleteId;
import com.board.boardserver.dto.request.UserLoginRequest;
import com.board.boardserver.dto.request.UserUpdatePasswordRequest;
import com.board.boardserver.dto.response.LoginResponse;
import com.board.boardserver.dto.response.UserInfoResponse;
import com.board.boardserver.service.impl.UserServiceImpl;
import com.board.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserServiceImpl userService;
    private static LoginResponse LoginResponse;

    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO){
        if(userDTO.hasNullDataBeforeRegister(userDTO)){
            throw new RuntimeException("회원가입 정보를 확인해주세요.");
        }
        userService.register(userDTO);
    }

    @PostMapping("sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest userLoginRequest, HttpSession session){
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();
        UserDTO userInfo = userService.login(id, password);

        if (userInfo == null){
            return HttpStatus.NOT_FOUND;
        } else if (userInfo != null) {
            LoginResponse = LoginResponse.success(userInfo);
            if(userInfo.getStatus() == (UserDTO.Status.ADMIN))
                SessionUtil.setLoginAdminId(session, id);
            else
                SessionUtil.setLoginMemberId(session, id);

            responseEntity = new ResponseEntity<LoginResponse>(LoginResponse, HttpStatus.OK );
        }else {
            throw new RuntimeException("Login ERROR! 유저 정보가 없거나 지원되지 않는 유저입니다.");
        }
        return HttpStatus.OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse memberInfo(HttpSession session){
        String id = SessionUtil.getLoginMemberId(session);
        if(id == null) id = SessionUtil.getLoginAdminId(session);
        UserDTO memberInfo = userService.getUserInfo(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("logout")
    public void logout( HttpSession session){
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<LoginResponse> updateUserPassword(String accountId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest, HttpSession session){
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = accountId;
        String beforepassword = userUpdatePasswordRequest.getBeforePassword();
        String afterpassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(id, beforepassword, afterpassword);
            ResponseEntity.ok(new ResponseEntity<LoginResponse>(LoginResponse, HttpStatus.OK));
        } catch (IllegalArgumentException e){
            log.error("updatePassword 실패", e);
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId, HttpSession session){
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(id, userDeleteId.getPassword());
            ResponseEntity.ok(new ResponseEntity<LoginResponse>(LoginResponse, HttpStatus.OK));
        } catch (RuntimeException e){
            log.error("deleteId 실패");
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

}
