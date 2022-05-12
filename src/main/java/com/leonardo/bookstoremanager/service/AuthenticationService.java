package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.dto.AuthenticatedUser;
import com.leonardo.bookstoremanager.dto.JwtRequest;
import com.leonardo.bookstoremanager.dto.JwtResponse;
import com.leonardo.bookstoremanager.entitys.User;
import com.leonardo.bookstoremanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    public JwtResponse createAuthenticationToken(JwtRequest jwtRequest){
        String userName = jwtRequest.getUsername();
        authenticate(userName, jwtRequest.getPassword());

        UserDetails userDetails = this.loadUserByUsername(userName);
        String token = jwtTokenManager.generateToken(userDetails);

        return JwtResponse.builder()
                .jwtToken(token)
                .build();
    }

    private void authenticate(String userName, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User not found with username %s", userName)));
        return new AuthenticatedUser(
                user.getUserName(),
                user.getPassword(),
                user.getRole().getDescription()
        );
    }
}
