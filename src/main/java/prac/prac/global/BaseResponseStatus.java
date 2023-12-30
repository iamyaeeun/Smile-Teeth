package prac.prac.global;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    RTK_INCORRECT(false, 2004, "Refresh Token 값을 확인해주세요."),

    USERS_NOT_FOUND_EMAIL(true,2010,"가입 가능한 이메일입니다."),
    USERS_EXISTS_EMAIL(false,2011,"이미 존재하는 메일 주소입니다."),
    EMPTY_INFORMATION(false, 2050, "요청값이 존재하지 않습니다."),
    CANNOT_FIND_INFORMATION(false, 2051, "해당 항목이 존재하지 않습니다."),
    USERS_NOT_EXISTS(false,2012,"회원 정보가 존재하지 않습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
