package prac.prac.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prac.prac.dto.UserReq;
import prac.prac.dto.UserRes;
import prac.prac.entities.User;
import prac.prac.global.BaseException;
import prac.prac.jwt.JWTUtil;
import prac.prac.repository.UserRepository;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import static prac.prac.global.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder encoder;
    private final JWTUtil jwtUtil;

    // 회원가입
    public void join(UserReq.UserJoinReq userJoinReq) throws BaseException, IOException {

        Optional<User> userOptional = userRepository.findByEmail(userJoinReq.getEmail());

        if (userOptional.isEmpty()) {
            User user = User.builder()
                    .email(userJoinReq.getEmail())
                    .password(encoder.encode(userJoinReq.getPassword()))
                    .name(userJoinReq.getName())
                    .role("ROLE_ADMIN")
                    .build();

            userRepository.saveAndFlush(user);
        } else {
            throw new BaseException(USERS_EXISTS_EMAIL);
        }
    }

    public UserRes.UserLoginRes login(UserReq.UserLoginReq userLoginReq) throws BaseException, IOException {

        Optional<User> userOptional = userRepository.findByEmailAndPassword(userLoginReq.getEmail(), userLoginReq.getPassword());
        log.info(userOptional.toString());

        if (userOptional.isEmpty() || !encoder.matches(userLoginReq.getPassword(), userOptional.get().getPassword())) {
            throw new BaseException(USERS_NOT_EXISTS);
        } else {
            User user = userOptional.get();
            String token = jwtUtil.createJwt(user.getEmail(), user.getRole(), 60 * 60 * 10L);
            UserRes.UserLoginRes userLoginRes = new UserRes.UserLoginRes(user.getUserId(), token);
            return userLoginRes;
        }
    }

    public UserRes.UserEmailCodeRes sendCodeToEmail(String email, boolean isForJoin) throws BaseException {
        this.checkDuplicatedEmail(email, isForJoin);

        String title = "[NEWKEY] 이메일 인증 번호 안내";
        String authCode = this.createCode();
        String imageUrl = "static/newkey.png";

        String content = "<html>"
                + "<body>"
                + "<h2>이메일 인증 번호 안내</h2>"
                + "<p>하단에 안내된 6자리 인증 번호를 입력해주세요.</p>"
                + "<h1>" + authCode + "</h1>"
                + "</body>"
                + "</html>";

        UserRes.UserEmailCodeRes res = new UserRes.UserEmailCodeRes(authCode);

        emailService.sendEmail(email, title, imageUrl, content);

        return res;
    }

    private void checkDuplicatedEmail(String email, boolean isForJoin) throws BaseException {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (isForJoin) { // 회원가입에 대한 이메일 인증인 경우
            if (userOptional.isPresent()) {
                throw new BaseException(USERS_EXISTS_EMAIL); // 이미 존재함 -> 회원가입 불가능
            }
        } else { // 비밀번호 찾기에 대한 이메일 인증인 경우
            if (userOptional.isEmpty()) {
                throw new BaseException(USERS_NOT_EXISTS); // 가입되지 않은 회원 -> 비밀번호 찾기 불가능
            }
        }
    }

    private String createCode() throws BaseException {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BaseException(ERR_MAKE_CODE);
        }
    }

    public Boolean verifiedCode(String correctCode, String inputCode) throws BaseException {
        if (correctCode.equals(inputCode)) {
            return true;
        } else {
            throw new BaseException(INCORRECT_CODE);
        }
    }

    public Boolean checkDupEmail(String email) throws BaseException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false;
        }
        return true;
    }

    @Transactional
    public void changePassword(String email, String password) throws BaseException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new BaseException(USERS_NOT_EXISTS);
        }
        User user = userOptional.get();
        user.setPassword(encoder.encode(password));
    }

    // 프로필 설정
    @Transactional
    public void setProfile(UserReq.UserProfileReq userProfileReq) throws BaseException, IOException {

        Optional<User> userOptional = userRepository.findByEmail(userProfileReq.getEmail());

        if (userOptional.isEmpty()) {
            throw new BaseException(USERS_NOT_EXISTS);
        } else {
            userOptional.get().setProfile(userProfileReq.getProfile());
        }
    }

    // 이름 설정
    @Transactional
    public void setName(UserReq.UserNameReq userNameReq) throws BaseException, IOException {

        Optional<User> userOptional = userRepository.findByEmail(userNameReq.getEmail());

        if (userOptional.isEmpty()) {
            throw new BaseException(USERS_NOT_EXISTS);
        } else {
            userOptional.get().setName(userNameReq.getName());
        }
    }

    public UserRes.UserInfoCheckRes info(String email) throws BaseException, IOException {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new BaseException(USERS_NOT_EXISTS);
        } else {
            UserRes.UserInfoCheckRes userInfoCheckRes = new UserRes.UserInfoCheckRes(userOptional.get().getName(),userOptional.get().getProfile());
            return userInfoCheckRes;
        }
    }

    @Transactional
    public void delete(UserReq.UserDeleteReq userDeleteReq) throws BaseException, IOException {
        Optional<User> userOptional = userRepository.findByUserId(userDeleteReq.getUserIdx());

        if(userOptional.isEmpty()){
            throw new BaseException(USERS_NOT_EXISTS);
        } else {
            userRepository.deleteById(userOptional.get().getUserId());
        }
    }
}
