package com.example.sl.domain.service;

import com.example.sl.domain.dto.UserAdultJoinRequest;
import com.example.sl.domain.dto.UserChildJoinRequest;
import com.example.sl.domain.dto.UserProfileUpdateRequest;
import com.example.sl.entity.User;

import com.example.sl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService; // 이메일 서비스 추가

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;

    @Transactional(rollbackFor=Exception.class)
    public boolean userAdultJoin(UserAdultJoinRequest requestDto){
        // 사용자 아이디 또는 이메일이 이미 존재하는지 확인
        if (userRepository.existsByUserName(requestDto.getUserName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "사용자 아이디가 이미 존재합니다.");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이메일이 이미 존재합니다.");
        }

        User user = new User();
        user.setUserName(requestDto.getUserName());
        user.setPassword(encoder.encode(requestDto.getPassword()));
        user.setRealname(requestDto.getRealname());
        user.setBirth(requestDto.getBirth());
        user.setChildphone(null);
        user.setAdultphone(requestDto.getAdultphone());
        user.setAddr(requestDto.getAddr());
        user.setEmail(requestDto.getEmail());
        user.setGender(requestDto.getGender());
        user.setRole("ROLE_USER");
        user.setIslocked(false);
        user.setMembership("일반");

        userRepository.save(user);

        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public boolean userChildJoin(UserChildJoinRequest requestDto){
        // 사용자 아이디 또는 이메일이 이미 존재하는지 확인
        if (userRepository.existsByUserName(requestDto.getUserName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "사용자 아이디가 이미 존재합니다.");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이메일이 이미 존재합니다.");
        }

        // 사용자 등록 로직
        User user = new User();
        user.setUserName(requestDto.getUserName());
        user.setPassword(encoder.encode(requestDto.getPassword()));
        user.setRealname(requestDto.getRealname());
        user.setBirth(requestDto.getBirth());
        user.setChildphone(requestDto.getChildphone());
        user.setAdultphone(requestDto.getAdultphone());
        user.setAddr(requestDto.getAddr());
        user.setEmail(requestDto.getEmail());
        user.setGender(requestDto.getGender());
        user.setRole("ROLE_USER");
        user.setIslocked(false);
        user.setMembership("일반");

        userRepository.save(user);

        return true;
    }

    public User getUserInfo(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String findUsername(String realname, LocalDate birth, String email) {
        Optional<User> user = userRepository.findByRealnameAndBirthAndEmail(realname, birth, email);
        return user.map(User::getUserName).orElse(null);
    }

    // 비밀번호 재설정 기능
    public void resetPassword(String userName, String email) {
        Optional<User> userOptional = userRepository.findByUserNameAndEmail(userName, email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String tempPassword = generateTempPassword();
            updatePassword(user, tempPassword);
            emailService.sendEmail(user.getEmail(), "임시 비밀번호", "임시 비밀번호는: " + tempPassword + " 입니다.");
        } else {
            throw new UsernameNotFoundException("해당 사용자 아이디와 이메일로 등록된 사용자를 찾을 수 없습니다: " + email);
        }
    }

    private String generateTempPassword() {
        Random random = new Random();
        int length = 10;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) (random.nextInt(26) + 'a'));
        }
        return sb.toString();
    }

    @Transactional(rollbackFor=Exception.class)
    public void updatePassword(String username, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자 아이디를 찾을 수 없습니다: " + username));

        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateUserProfile(String username, UserProfileUpdateRequest request) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setEmail(request.getEmail());
        user.setAdultphone(request.getPhoneNumber());
        user.setAddr(request.getAddress());
        user.setRealname(request.getRealName());
        user.setBirth(request.getBirthDate());
        user.setGender(request.getGender());

        userRepository.save(user);
    }

}
