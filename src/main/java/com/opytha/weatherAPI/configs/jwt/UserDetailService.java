package com.opytha.weatherAPI.configs.jwt;

import com.opytha.weatherAPI.models.Client;
import com.opytha.weatherAPI.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username);

        if (client == null){
            throw new UsernameNotFoundException(username);
        }

        User.UserBuilder builder = User.withUsername(username); // Fully qualify the Spring Security Client
        builder.password(client.getPassword()); // Use the custom client’s password
        builder.roles("CLIENT"); // Assign role

        return builder.build();
    }
}
