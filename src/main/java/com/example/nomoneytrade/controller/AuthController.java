package com.example.nomoneytrade.controller;

import com.example.nomoneytrade.entity.Role;
import com.example.nomoneytrade.entity.User;
import com.example.nomoneytrade.imageStorage.StorageService;
import com.example.nomoneytrade.payload.requests.SignInRequest;
import com.example.nomoneytrade.payload.requests.SignUpRequest;
import com.example.nomoneytrade.payload.responses.BaseResponse;
import com.example.nomoneytrade.payload.responses.UserCredentials;
import com.example.nomoneytrade.repository.RoleRepository;
import com.example.nomoneytrade.repository.UserRepository;
import com.example.nomoneytrade.service.UserDetailsImpl;
import com.example.nomoneytrade.utils.JwtUtils;
import com.example.nomoneytrade.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.nomoneytrade.utils.Constants.IMAGE_HOST_URI;

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

    private final StorageService storageService;

    @Autowired
    public AuthController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody @Valid SignInRequest signInRequest) {
        Authentication authentication;
        String login = signInRequest.getLogin();
        String password = signInRequest.getPassword();
        if (isEmail(login)) {
            try {
                User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User with this email does`t exist"));
                authentication = authenticator.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), password));
            } catch (RuntimeException e) {
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } else {
            authentication = authenticator.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        }

        // in this context user is saved (securely). Setting user for it
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // getting user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.isEnabled()) {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }

        String jwtCookie = jwtUtils.generateJwtCookie(userDetails).toString();
        // getting authorities (roles)
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie)
                .body(new UserCredentials(
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.isEnabled(),
                        userDetails.getPassword(),
                        userDetails.getImagePath()
                ));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        // base cookies. cleaning them after signing out
        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new BaseResponse("You've been signed out."));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestParam("user_data") @Valid SignUpRequest signUpRequest, @RequestParam MultipartFile file) {
        String email = signUpRequest.getEmail();
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("This email is occupied.");
        }

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body("This username is taken.");
        }



        User user = new User(username, email, passwordEncoder.encode(password), "");

        String imagePath = IMAGE_HOST_URI + "avatar_" + user.getId().toString();
        storageService.store(file, "avatar_" + user.getId().toString());
        user.setImagePath(imagePath);

        // giving base ROLE_USER to new user
        HashSet<Role> roles = new HashSet<>();
        Role baseUserRoles = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("This role does`t exist"));
        roles.add(baseUserRoles);
        user.setRoles(roles);

        userRepository.save(user);

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();


        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new UserCredentials(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBanned(),
                user.getPassword(),
                user.getImagePath()
        ));
    }

    public Boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }
}
