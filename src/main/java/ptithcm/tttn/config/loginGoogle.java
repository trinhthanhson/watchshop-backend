package ptithcm.tttn.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.response.AuthResponse;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.UserService;
import ptithcm.tttn.service.impl.UserDetailsServiceImpl;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class loginGoogle {

    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public loginGoogle(UserService userService, UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, CustomerService customerService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    @GetMapping("/user")
    public Map<String, Object> user(OAuth2AuthenticationToken principal) {
        if (principal == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        return principal.getPrincipal().getAttributes();
    }
//    @GetMapping("/login/success")
//    public ResponseEntity<AuthResponse> loginSuccess(OAuth2AuthenticationToken principal) throws Exception {
//        AuthResponse res = new AuthResponse();
//
//        // Add logging to debug the principal object
//        if (principal == null) {
//            throw new IllegalStateException("OAuth2AuthenticationToken cannot be null");
//        }
//
//        // Check the OAuth2 principal attributes
//        OAuth2User oAuth2User = principal.getPrincipal();
//        if (oAuth2User == null || oAuth2User.getAttributes().isEmpty()) {
//            throw new IllegalStateException("OAuth2 user details are missing");
//        }
//
//        // Debug print attributes to check what is available
//        System.out.println("OAuth2 Attributes: " + oAuth2User.getAttributes());
//
//        String email = oAuth2User.getAttribute("email");
//        if (email == null) {
//            throw new IllegalStateException("Email attribute is missing in OAuth2 token");
//        }
//
//        Authentication authentication = authenticate(email);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String tokenSevenday = jwtTokenProvider.generateAccessToken(authentication);
//        LocalDateTime currentTime = LocalDateTime.now();
//        LocalDateTime expiredAccessToken = currentTime.plus(7, ChronoUnit.DAYS);
//        Timestamp expiredAccessTokenTimestamp = Timestamp.valueOf(expiredAccessToken);
//
//        res.setStatus(true);
//        res.setToken(tokenSevenday);
//
//        User user = userService.findByUsername(email);
//        user.setAccess_token(tokenSevenday);
//        user.setExpired_date(expiredAccessToken);
//        userService.signIn(user);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", "http://localhost:5173/home");
//        return new ResponseEntity<>(res, headers, HttpStatus.FOUND);
//    }

    private Authentication authenticate(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @GetMapping("/login/failure")
    public String loginFailure() {
        return "Login failed!";
    }
}
