package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Repositories.UserRepository;
import com.soulcode.Servicos.Security.UserSecurityDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username); // filtro por email
        if (user.isEmpty()) { // ou use "!user.isPresent()"
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        // user.get().getStatus();
        return new UserSecurityDetail(user.get().getLogin(), user.get().getPassword(), user.get().isStatusAccount());
    }

}
