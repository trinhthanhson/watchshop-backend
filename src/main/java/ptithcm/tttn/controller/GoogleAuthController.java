package ptithcm.tttn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.config.JwtTokenProvider;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.function.GenerateOtp;
import ptithcm.tttn.request.SignUpRequest;
import ptithcm.tttn.response.AuthResponse;
import ptithcm.tttn.service.UserService;
import ptithcm.tttn.service.impl.UserDetailsServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
public class GoogleAuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    public GoogleAuthController(JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/oauth2/login/success")
    public ResponseEntity<AuthResponse> handleOAuth2LoginSuccess(Authentication authentication) {
        OAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        SignUpRequest rq = new SignUpRequest();
        AuthResponse res = new AuthResponse();

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        try {
            User existingUser = userService.findByUsername(email);
            if (existingUser != null) {
                // User exists, authenticate and generate token
                return processExistingUser(existingUser, res);
            } else {
                // User does not exist, create a new user
                return processNewUser(email, name, rq, res);
            }
        } catch (Exception e) {
            res.setStatus(false);
            res.setToken(null); // Handle error case
            e.printStackTrace(); // Log the error for debugging
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<AuthResponse> processExistingUser(User existingUser, AuthResponse res) {
        Authentication auth = authenticate(existingUser.getUsername(), existingUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtTokenProvider.generateAccessToken(auth);

        LocalDateTime expirationTime = LocalDateTime.now().plus(7, ChronoUnit.DAYS);
        existingUser.setAccess_token(token);
        existingUser.setExpired_date(expirationTime);
        userService.signIn(existingUser);

        res.setStatus(true);
        res.setToken(token);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private ResponseEntity<AuthResponse> processNewUser(String email, String name, SignUpRequest rq, AuthResponse res) throws Exception {
        rq.setEmail(email);
        rq.setUsername(email);
        rq.setPassword(passwordEncoder.encode(GenerateOtp.generateOTP())); // Generate a temporary password
        rq.setLastname(name);
        rq.setRole_name("CUSTOMER");

        User newUser = userService.createUser(rq);
        Authentication auth = authenticate(newUser.getUsername(), rq.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtTokenProvider.generateAccessToken(auth);

        LocalDateTime expirationTime = LocalDateTime.now().plus(7, ChronoUnit.DAYS);
        newUser.setAccess_token(token);
        newUser.setExpired_date(expirationTime);
        userService.signIn(newUser);

        res.setStatus(true);
        res.setToken(token);
        System.out.println("ddd: " + res.getToken());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
