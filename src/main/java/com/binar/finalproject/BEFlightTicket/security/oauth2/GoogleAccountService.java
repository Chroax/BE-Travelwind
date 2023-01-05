package com.binar.finalproject.BEFlightTicket.security.oauth2;

import com.binar.finalproject.BEFlightTicket.dto.GoogleLoginRequest;
import com.binar.finalproject.BEFlightTicket.dto.UserResponse;
import com.binar.finalproject.BEFlightTicket.model.AuthenticationProvider;
import com.binar.finalproject.BEFlightTicket.model.Roles;
import com.binar.finalproject.BEFlightTicket.model.Users;
import com.binar.finalproject.BEFlightTicket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class GoogleAccountService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse oAuthLoginSuccess(GoogleLoginRequest googleLoginRequest) {
        try{
            Users userGoogle = userRepository.findByGoogleId(googleLoginRequest.getGooleId());
            if(userGoogle == null)
            {
                Users users = googleLoginRequest.toUsersGoogle();
                Roles roles = new Roles();
                users.setEmail(googleLoginRequest.getEmail());
                users.setFullName(googleLoginRequest.getFullName());
                users.setStatusActive(true);
                users.setAuthProvider(AuthenticationProvider.GOOGLE);
                users.setGoogleId(googleLoginRequest.getGooleId());
                roles.setRoleName("ROLE_BUYER");
                oAuth2Password(users);
                userRepository.save(users);
                return UserResponse.build(users);
            }
            else
                return null;
        }
        catch (Exception ignore){
            return null;
        }
    }
    public void oAuth2Password(Users users){
        String googleId = users.getGoogleId();
        String clientId = "953090499155-f5pgpt16s6lhge53hhi4s5cm5dg18in3.apps.googleusercontent.com";
        String googlePassword = googleId + clientId;
        users.setPassword(String.valueOf(googlePassword.hashCode()));
    }
}
