package base.controller;

import base.entity.Role;
import base.entity.User;
import base.entity.UserConnection;
import base.service.UserConnectionService;
import base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.*;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.facebook.security.FacebookAuthenticationService;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.security.provider.SocialAuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@SessionAttributes("facebookToken")
public class MainController {
    @Autowired
    Environment env;
    @Autowired
    UserService userService;
    @Autowired
    UserConnectionService userConnectionService;
    @Autowired
    ConnectionSignUp signUp;

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/fb/login")
    public void fbLogin(HttpServletResponse response) throws IOException {
        FacebookConnectionFactory factory = new FacebookConnectionFactory(
                env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"),
                env.getProperty("facebook.app.namespace")
        );
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri("http://localhost:8080/fb/callback");
        parameters.setScope("public_profile");
        OAuth2Operations authOperations = factory.getOAuthOperations();
        String authorizeUrl = authOperations.buildAuthorizeUrl(parameters);
        response.sendRedirect(authorizeUrl);
    }

    @GetMapping("/fb/callback")
    public String fbCallback(@RequestParam("code") String code, Model model) throws IOException {
        FacebookConnectionFactory factory = new FacebookConnectionFactory(
                env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"),
                env.getProperty("facebook.app.namespace")
        );
        OAuth2Operations authOperations = factory.getOAuthOperations();
        AccessGrant accessGrant = authOperations
                .exchangeForAccess(code, "http://localhost:8080/fb/callback", null);
        Connection<Facebook> connection = factory.createConnection(accessGrant);
        String token = accessGrant.getAccessToken();
        Facebook facebook = connection.getApi();
        if (facebook.isAuthorized()) {
            signUp.execute(connection);
            return "shalom";
        } else {
            return "redirect:/fb/login";
        }
    }

    @PostMapping("/protected")
    public String protect() {
        return "shalom";
    }
}
