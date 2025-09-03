package com.selfProjectbook.SelfProjecttwoBook.security;

import com.selfProjectbook.SelfProjecttwoBook.dto.LoginRequestDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.LoginResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.dto.SignupResponseDto;
import com.selfProjectbook.SelfProjecttwoBook.entity.User;
import com.selfProjectbook.SelfProjecttwoBook.entity.type.AuthProviderType;
import com.selfProjectbook.SelfProjecttwoBook.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(), loginRequestDto.getPassword()
                )
        );
        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateToken(user);
        return new LoginResponseDto(token, user.getId());
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto, String role) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername())
                .orElse(null);
        if (user != null) throw new IllegalArgumentException("User already exists");

        user = userRepository.save(User.builder()
                .username(signupRequestDto.getUsername())
                .password(signupRequestDto.getPassword() != null ?
                        passwordEncoder.encode(signupRequestDto.getPassword()) : null) // password can be null for OAuth
                .role(role)
                .build());
        return new SignupResponseDto(user.getUsername(), user.getId());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest
            (OAuth2User oAuth2User, String registrationId) {

        // 1. Identify provider info
        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        // 2. Try finding user by providerId + providerType
        User user = userRepository.findByProviderIdAndAuthProviderType(providerId, providerType)
                .orElse(null);

        // 3. Check email-based account conflict
        String email = oAuth2User.getAttribute("email");
        User emailUser = (email != null) ? userRepository.findByUsername(email).orElse(null) : null;

        if (user == null && emailUser == null) {
            // new signup flow
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);

            user = User.builder()
                    .username(username)
                    .providerId(providerId)
                    .authProviderType(providerType)
                    .role("USER") // default role
                    .build();

            userRepository.save(user);

        } else if (user != null) {
            // existing user: update email if required
            if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
                user.setUsername(email);
                userRepository.save(user);
            }
        } else {
            // email exists with another provider
            throw new BadCredentialsException("This email is already registered with another provider: " + email);
        }

        // 4. Generate token for user
        String token = authUtil.generateToken(user);
        LoginResponseDto loginResponseDto = new LoginResponseDto(token, user.getId());

        return ResponseEntity.ok(loginResponseDto);
    }
}
