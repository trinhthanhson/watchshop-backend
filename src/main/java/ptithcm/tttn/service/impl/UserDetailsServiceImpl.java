package ptithcm.tttn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ptithcm.tttn.entity.Role;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.RoleRepo;
import ptithcm.tttn.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepo userRepository;
    private RoleRepo roleRepo;

    public UserDetailsServiceImpl(UserRepo userRepository, RoleRepo roleRepo) {
        this.userRepository = userRepository;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        String roleName = "";
        try {
            Optional<Role> findRole = roleRepo.findById(user.getRole_id());
            Role role = findRole.get();
            roleName = role.getRole_name();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority( roleName));
        System.err.println(roleName);

        System.err.println(authorities);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}

