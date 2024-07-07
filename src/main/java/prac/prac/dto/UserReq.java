package prac.prac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserReq {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserJoinReq { //회원가입
        String email;
        String password;
        String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserLoginReq { //로그인
        String email;
        String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserSendEmailReq { // 이메일 전송 요청
        String email;
        Boolean isForJoin;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserInfoCheckReq { // 회원 정보 조회 요청
        String email;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserEmailCodeCheckReq { // 인증 코드 검증 요청
        String correctCode;
        String inputCode;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserEmailDupCheckReq { // 이메일 중복 확인
        String email;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserPasswordChangeReq { // 비밀번호 변경
        String email;
        String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserDeleteReq { // 회원 탈퇴 요청
        Long userIdx;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserProfileReq { // 프로필 사진 저장 요청
        String email;
        String profile;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserNameReq { // 사용자명 저장 요청
        String email;
        String name;
    }
}
