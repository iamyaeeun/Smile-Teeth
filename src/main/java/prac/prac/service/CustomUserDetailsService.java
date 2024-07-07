package prac.prac.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import prac.prac.dto.CustomUserDetails;
import prac.prac.entities.User;
import prac.prac.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //DB에서 조회
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            User user = userOptional.get();
            return new CustomUserDetails(user);
        }

        return null;
    }
}