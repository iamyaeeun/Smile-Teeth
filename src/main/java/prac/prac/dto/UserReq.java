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
        private String name;
        private String phone;
        private Long age;
        private boolean ortho;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserLoginReq { //로그인
        String email;
        String password;
    }
}
