package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sys_api_restaurant.security.JwtService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    public void logout(String token) {
        log.info("Logout requested for token: {}", token);
    }
}
