package prac.prac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserRes {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserLoginRes { //로그인
        Long userIdx;
    }
}
