package uz.tridev.eventbooking.service.impl;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tridev.eventbooking.dto.request.LoginDto;
import uz.tridev.eventbooking.dto.request.RegisterDto;

public interface AuthServiceI {
    ResponseEntity<?> register(@Valid RegisterDto dto);
    ResponseEntity<?> login(@Valid LoginDto loginDto);
}
