package com.bank.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bank.security.domain.Role;
import com.bank.security.domain.User;
import com.bank.security.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class UserController {

    UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("user/{username}/find")
    public ResponseEntity<User> findByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUSer(@Valid @RequestBody User user)
    {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@Valid @RequestBody Role role){
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@Valid @RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm=Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(Long.valueOf(username));

                String access_token= JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", String.valueOf(user.getRoles().stream().map(Role::getName).collect(Collectors.toList())))
                        .sign(algorithm);
                Map<String,String> tokens= new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception exception){
                log.error("Error logging in: {}",exception.getMessage());
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String,String> error= new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}
