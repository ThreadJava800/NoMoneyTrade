package com.example.nomoneytrade.controller;

import com.example.nomoneytrade.entity.Role;
import com.example.nomoneytrade.entity.User;
import com.example.nomoneytrade.payload.requests.SignInRequest;
import com.example.nomoneytrade.payload.requests.SignUpRequest;
import com.example.nomoneytrade.payload.responses.UserCredentials;
import com.example.nomoneytrade.repository.RoleRepository;
import com.example.nomoneytrade.repository.UserRepository;
import com.example.nomoneytrade.service.UserDetailsImpl;
import com.example.nomoneytrade.utils.JwtUtils;
import com.example.nomoneytrade.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody @Valid SignInRequest signInRequest) {
        Authentication authentication = authenticator.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.isEnabled()) {
            return ResponseEntity.badRequest().body("This user is banned");
        }

        String jwtCookie = jwtUtils.generateJwtCookie(userDetails).toString();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie)
                .body(new UserCredentials(
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.isEnabled(),
                        userDetails.getEmail(),
                        roles
                ));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body("You've been signed out!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("This email is occupied!");
        }

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body("This username is taken!");
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        HashSet<Role> roles = new HashSet<>();
        Role baseUserRoles = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("This role does`t exist"));
        roles.add(baseUserRoles);
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok("Success!");
    }
}
