package org.example.ecommerce_be.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ecommerce_be.Entity.Role;
import org.example.ecommerce_be.Entity.User;
import org.example.ecommerce_be.Entity.UserRole;
import org.example.ecommerce_be.Enum.ERole;
import org.example.ecommerce_be.Repository.RoleRepository;
import org.example.ecommerce_be.Repository.UserRepository;
import org.example.ecommerce_be.Repository.UserRoleRepository;
import org.example.ecommerce_be.Request.LoginRequest;
import org.example.ecommerce_be.Request.SignupRequest;
import org.example.ecommerce_be.Response.JwtResponse;
import org.example.ecommerce_be.Response.MessageResponse;
import org.example.ecommerce_be.Response.UserResponse;
import org.example.ecommerce_be.Security.UserDetailsImpl;
import org.example.ecommerce_be.Service.JWT.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
   private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
   private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
   private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            // Authenticate the user based on username and password provided
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // SecurityContextHolder holds security information of the current session
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateToken(userDetails);

            // Get user roles
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            UserResponse ur = new UserResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);

            // Return a response with JWT token and user roles
            return ResponseEntity.ok(new JwtResponse(jwt, ur));

        }  catch (BadCredentialsException e) {
        // Handle invalid credentials
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid credentials"));
    } catch (UsernameNotFoundException e) {
        // Handle user not found
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("User Not Found"));
    } catch (AuthenticationException e) {
        // Handle other authentication exceptions
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Authentication failed"));
    }

    }
    @Transactional
    //thực thi thành công hết hoặc là không có bất cứ hành động nào được thực khi có bất kỳ một hoạt động thực thi không thành công.
    public ResponseEntity<?> register(SignupRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        User user =  new User(signUpRequest.getUserName(),signUpRequest.getFullName(), signUpRequest.getFirstName(),
               encoder.encode( signUpRequest.getPassword()), signUpRequest.getMobile(),signUpRequest.getEmail()
                , signUpRequest.getAddress(), signUpRequest.getBirthDate(), signUpRequest.getGender());

        // set mac dinh cho role la "USER"
        Role role = roleRepository.findByName(ERole.USER);
        UserRole userRole = new UserRole(role.getId(),user.getId());



        try {
            userRepository.save(user);
            userRoleRepository.save(userRole);

        } catch (Exception e) {
            // Rollback transaction if any exception occurs
            throw new RuntimeException("Error: Unable to register user", e);
        }


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Transactional
    //thực thi thành công hết hoặc là không có bất cứ hành động nào được thực khi có bất kỳ một hoạt động thực thi không thành công.
    public ResponseEntity<?> registerAdmin(SignupRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        User user =  new User(signUpRequest.getUserName(),signUpRequest.getFullName(), signUpRequest.getFirstName(),
                encoder.encode( signUpRequest.getPassword()), signUpRequest.getMobile(),signUpRequest.getEmail()
                , signUpRequest.getAddress(), signUpRequest.getBirthDate(), signUpRequest.getGender());


        Role role = roleRepository.findByName(ERole.ADMIN);
        UserRole userRole = new UserRole(role.getId(),user.getId());



        try {
            userRepository.save(user);
            userRoleRepository.save(userRole);

        } catch (Exception e) {
            // Rollback transaction if any exception occurs
            throw new RuntimeException("Error: Unable to register user", e);
        }


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    }

