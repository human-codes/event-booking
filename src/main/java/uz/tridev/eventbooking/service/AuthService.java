package uz.tridev.eventbooking.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.tridev.eventbooking.dto.response.BaseResponse;
import uz.tridev.eventbooking.dto.request.LoginDto;
import uz.tridev.eventbooking.dto.request.RegisterDto;
import uz.tridev.eventbooking.entity.User;
import uz.tridev.eventbooking.entity.enums.RoleName;
import uz.tridev.eventbooking.repository.RoleRepository;
import uz.tridev.eventbooking.repository.UserRepository;
import uz.tridev.eventbooking.security.JwtService;
import uz.tridev.eventbooking.service.impl.AuthServiceI;

import java.util.Optional;

@Service
public class AuthService implements AuthServiceI {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<?> register(RegisterDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            return ResponseEntity.badRequest()
                    .body(new BaseResponse<>(false, "Username already taken", null));
        }

        if (userRepository.existsByEmail(dto.email())){
            return ResponseEntity.badRequest().body(new BaseResponse<>(false, "Email already in use", null));
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(roleRepository.findByRoleName((RoleName.ROLE_USER)));
        userRepository.save(user);
        return ResponseEntity.ok(new BaseResponse<>(true, "User registered successfully", null));
    }

    public ResponseEntity<?> login(LoginDto dto) {
        Optional<User> optionalUser = userRepository.findByUsername(dto.username());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>(false, "Invalid username or password", null));
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new BaseResponse<>(false, "Invalid username or password", null));
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new BaseResponse<>(true, "Login successful", token));
    }



}
