package prac.prac.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import prac.prac.dto.UserReq;
import prac.prac.dto.UserRes;
import prac.prac.global.BaseException;
import prac.prac.global.BaseResponse;
import prac.prac.service.UserService;

import java.io.IOException;

import static prac.prac.global.BaseResponseStatus.SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping("/")
    public String getHello() {
        return "Hello";
    }

    // 회원 가입
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

    // 이메일 전송
    @PostMapping("/emails/verification-requests")
    public BaseResponse<UserRes.UserEmailCodeRes> sendMessage(@RequestBody UserReq.UserSendEmailReq userSendEmailReq) throws BaseException {
        UserRes.UserEmailCodeRes res = userService.sendCodeToEmail(userSendEmailReq.getEmail(), userSendEmailReq.getIsForJoin());
        return new BaseResponse<>(res);
    }

    // 이메일 인증
    @PostMapping("/emails/verifications")
    public BaseResponse<UserRes.UserEmailCodeCheckRes> verificationEmail(@RequestBody UserReq.UserEmailCodeCheckReq userEmailCodeCheckReq) throws BaseException {
        Boolean isCorrected = userService.verifiedCode(userEmailCodeCheckReq.getCorrectCode(), userEmailCodeCheckReq.getInputCode());
        UserRes.UserEmailCodeCheckRes res = new UserRes.UserEmailCodeCheckRes(isCorrected);
        return new BaseResponse<>(res);
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public BaseResponse<UserRes.UserEmailDupCheckRes> checkDupEmail(@RequestParam("email") String email) throws BaseException {
        Boolean emailExists = userService.checkDupEmail(email);
        UserRes.UserEmailDupCheckRes res = new UserRes.UserEmailDupCheckRes(emailExists);
        return new BaseResponse<>(res);
    }

    // 비밀번호 변경
    @PatchMapping("/password")
    public BaseResponse<Void> changePassword(@RequestBody UserReq.UserPasswordChangeReq userPasswordChangeReq) throws BaseException {
        userService.changePassword(userPasswordChangeReq.getEmail(), userPasswordChangeReq.getPassword());
        return new BaseResponse<>(SUCCESS);
    }

    //회원 프로필 저장
    @PostMapping(value = "/profileSave")
    public BaseResponse<Void> profile(
            @RequestBody UserReq.UserProfileReq userProfileReq
    ) throws BaseException, IOException {
        userService.setProfile(userProfileReq);
        return new BaseResponse<>(SUCCESS);
    }

    //회원 이름 저장
    @PostMapping(value = "/nameSave")
    public BaseResponse<Void> name(
            @RequestBody UserReq.UserNameReq userNameReq
    ) throws BaseException, IOException {
        userService.setName(userNameReq);
        return new BaseResponse<>(SUCCESS);
    }

    //회원 정보 조회
    @GetMapping(value = "/info")
    public BaseResponse<UserRes.UserInfoCheckRes> info(@RequestParam("email") String email) throws BaseException, IOException {
        UserRes.UserInfoCheckRes userInfoCheckRes = userService.info(email);
        return new BaseResponse<>(userInfoCheckRes);
    }

    //회원 탈퇴
    @DeleteMapping(value = "/delete")
    public BaseResponse<Void> delete(
            @RequestBody UserReq.UserDeleteReq userDeleteReq
    ) throws BaseException, IOException {
        userService.delete(userDeleteReq);
        return new BaseResponse<>(SUCCESS);
    }
}
