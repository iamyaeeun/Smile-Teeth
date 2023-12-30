package prac.prac.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prac.prac.dto.UserReq;
import prac.prac.dto.UserRes;
import prac.prac.global.BaseException;
import prac.prac.global.BaseResponse;
import prac.prac.global.BaseResponseStatus;
import prac.prac.service.UserService;

import java.io.IOException;

import static prac.prac.global.BaseResponseStatus.SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping(value = "/join")
    public BaseResponse<Void> join(
            @RequestBody UserReq.UserJoinReq userJoinReq
    ) throws BaseException, IOException {
        userService.join(userJoinReq);
        return new BaseResponse<>(SUCCESS);
    }

    //로그인
    @PostMapping(value = "/login")
    public BaseResponse<UserRes.UserLoginRes> login(
            @RequestBody UserReq.UserLoginReq userLoginReq
    ) throws BaseException, IOException {
        UserRes.UserLoginRes userLoginRes = userService.login(userLoginReq);
        return new BaseResponse<>(userLoginRes);
    }
}
