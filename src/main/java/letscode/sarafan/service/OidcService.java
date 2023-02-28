package letscode.sarafan.service;

import letscode.sarafan.domain.Usr;
import letscode.sarafan.domain.UsrRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// used for Google
@Service
public class OidcService extends OidcUserService {
    private final UsrRepository usrRepository;

    public OidcService(UsrRepository usrRepository) {
        this.usrRepository = usrRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String id = oidcUser.getName(); //returns sub - An identifier for the user, unique among all Google accounts and never reused.
        Usr usr = usrRepository
                .findById(id)
                .orElse(
                        Usr.builder()
                                .id(id)
                                .email(oidcUser.getEmail())
                                .name(oidcUser.getFullName())
                                .locale(oidcUser.getLocale())
                                .userpic(oidcUser.getPicture())
                                .build());
        usr.setLastVisit(LocalDateTime.now());

        return usrRepository.save(usr);
    }
}
