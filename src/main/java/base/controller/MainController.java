package base.controller;

import base.service.UserConnectionService;
import base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    UsersConnectionRepository repository;
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
        parameters.setScope("public_profile,email,user_posts");
        OAuth2Operations authOperations = factory.getOAuthOperations();
        String authorizeUrl = authOperations.buildAuthorizeUrl(parameters);
        response.sendRedirect(authorizeUrl);
    }

    @GetMapping("/fb/callback")
    public String fbCallback(@RequestParam("code") String code, Model model, WebRequest request) throws IOException {
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
            String id = signUp.execute(connection);
            PagedList<Post> feed = facebook.feedOperations().getFeed();
            List<String> images = feed.stream().map(post -> post.getPicture()).collect(Collectors.toList());
            model.addAttribute("images",images);
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
