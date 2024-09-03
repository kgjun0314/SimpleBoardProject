package com.project.board.service;

import com.project.board.entity.SiteUser;
import com.project.board.entity.SiteUserRequestDto;
import com.project.board.entity.SiteUserResponseDto;
import com.project.board.entity.SiteUserRole;
import com.project.board.repository.SiteSiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SiteUserService implements UserDetailsService {

    private final SiteSiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createSiteUser(SiteUserRequestDto siteUserRequestDto) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(siteUserRequestDto.getUsername());
        siteUser.setPassword(passwordEncoder.encode(siteUserRequestDto.getPassword()));
        siteUser.setEmail(siteUserRequestDto.getEmail());
        siteUserRepository.save(siteUser);
    }

    public SiteUser getSiteUserEntityByUsername(String username) {
        Optional<SiteUser> siteUser = siteUserRepository.findByUsername(username);
        if(siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = siteUserRepository.findByUsername(username);
        if(_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(SiteUserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(SiteUserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
