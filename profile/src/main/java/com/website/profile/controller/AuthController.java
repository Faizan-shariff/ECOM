package com.website.profile.controller;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
import com.website.profile.dto.AuthRequest;
import com.website.profile.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//@Tag(
//        name = "CRUD REST API for Auth-Service",
//        description = "Functionality provided -\n1. Register a new user\n2. Delete an existing user\n3. Change password for existing user\n4. Update existing user's username and email\n5. Generate a token for existing user\n6. Validate the given token"
//)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

//    authenticationManager@Operation(
//            summary = "Generate token for existing user",
//            description = "Generates a token for an existing user by taking user email and password as inputs"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status 200 SUCCESS"
//    )
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) throws Exception{
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token = service.generateToken(authRequest.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            throw new Exception("invalid access");
        }
    }

//    @Operation(
//            summary = "Validate the provided token",
//            description = "Validates the token to confirm if it is genuine user or not"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "HTTP Status 200 SUCCESS"
//    )
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }
}
