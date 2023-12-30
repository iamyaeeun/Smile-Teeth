package prac.prac.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prac.prac.dto.UserReq;
import prac.prac.dto.UserRes;
import prac.prac.entities.User;
import prac.prac.global.BaseException;
import prac.prac.global.BaseResponseStatus;
import prac.prac.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

import static prac.prac.global.BaseResponseStatus.USERS_EXISTS_EMAIL;
import static prac.prac.global.BaseResponseStatus.USERS_NOT_EXISTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원가입
    public void join(UserReq.UserJoinReq userJoinReq) throws BaseException, IOException {

        Optional<User> userOptional = userRepository.findByEmail(userJoinReq.getEmail());

        if (userOptional.isEmpty()) {
            User user = User.builder()
                    .email(userJoinReq.getEmail())
                    .password(userJoinReq.getPassword())
                    .name(userJoinReq.getName())
                    .phone(userJoinReq.getPhone())
                    .age(userJoinReq.getAge())
                    .ortho(userJoinReq.isOrtho())
                    .build();

            userRepository.saveAndFlush(user);
        } else {
            throw new BaseException(USERS_EXISTS_EMAIL);
        }
    }

    //로그인
    public UserRes.UserLoginRes login(UserReq.UserLoginReq userLoginReq) throws BaseException, IOException{

        Optional<User> userOptional = userRepository.findByEmail(userLoginReq.getEmail());

        if(userOptional.isEmpty()) {
            throw new BaseException(USERS_NOT_EXISTS);
        } else {
            UserRes.UserLoginRes userLoginRes = new UserRes.UserLoginRes(userOptional.get().getId());
            return userLoginRes;
        }
    }
}
