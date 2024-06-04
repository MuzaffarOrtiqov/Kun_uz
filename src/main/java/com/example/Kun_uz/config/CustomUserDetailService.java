package com.example.Kun_uz.config;

import com.example.Kun_uz.entity.ProfileEntity;
import com.example.Kun_uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //muzaffarmike2@gmail.com
       Optional<ProfileEntity> optional=  profileRepository.findByEmailAndVisibleTrue(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        ProfileEntity employee = optional.get();
        return  new CustomUserDetail(employee);
    }
}
