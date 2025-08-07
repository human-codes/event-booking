package uz.tridev.eventbooking.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.tridev.eventbooking.dto.request.LoginDto;
import uz.tridev.eventbooking.dto.request.RegisterDto;
import uz.tridev.eventbooking.service.impl.AuthServiceI;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceI authService;


    public AuthController(AuthServiceI authService) {
        this.authService = authService;
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {
        return authService.login(loginDto);
    }


}
