//package com.bank.security.security;
//
//import com.bank.security.domain.User;
//import com.bank.security.service.UserService;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MyUserDetailsService implements UserDetailsService {
//    UserService userService;
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userService.findUserByUsername(username);
//        if (user==null)
//            throw new UsernameNotFoundException("Utilisateur introuvable !");
//        List<GrantedAuthority> auths = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            GrantedAuthority auhority = new
//                    SimpleGrantedAuthority(role.getRole());
//            auths.add(auhority);
//        });
//        return new org.springframework.security.core.
//                userdetails.User(user.getUsername(),user.getPassword(),auths);
//    }
//
//};
