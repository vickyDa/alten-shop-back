package com.alten.shop.controller;


//import com.example.demo.model.User;
//import com.example.demo.payload.AuthRequest;
//import com.example.demo.payload.AuthResponse;
//import com.example.demo.service.UserService;
//import com.example.demo.util.JwtTokenProvider;
import com.alten.shop.payload.AuthRequest;
import com.alten.shop.entity.User;
import com.alten.shop.payload.AuthResponse;
import com.alten.shop.repository.UserRepository;
import com.alten.shop.service.UserService;
import com.alten.shop.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final JwtUtils jwtUtils;
   private final AuthenticationManager authenticationManager;
   private final UserService userService;


    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok("Compte créé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/token")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if(authentication.isAuthenticated()){
                String token = jwtUtils.generateToken(authRequest.getEmail());
                return ResponseEntity.ok(new AuthResponse(token));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect.");
        }
    }
}
