package letscode.sarafan.service;

import letscode.sarafan.domain.Usr;
import letscode.sarafan.domain.UsrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

//Used for GitHub
@Service
public class SecurityService extends DefaultOAuth2UserService {
    @Autowired
    private UsrRepository usrRepository;

    @Autowired
    RestTemplate restTemplate;

    public SecurityService(UsrRepository usrRepository, RestTemplate restTemplate) {
        this.usrRepository = usrRepository;
        this.setRestOperations(restTemplate);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        Integer id = user.getAttribute("id");
        Usr usr = usrRepository
                .findById(id.toString())
                .orElse(
                        Usr.builder()
                                .id(id.toString())
                                .email(user.getAttribute("email"))
                                .name(user.getAttribute("login"))
                                .userpic(user.getAttribute("avatar_url"))
                                .build());
        usr.setLastVisit(LocalDateTime.now());

        return usrRepository.save(usr);
    }
}
