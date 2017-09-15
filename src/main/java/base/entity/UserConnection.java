package base.entity;

import lombok.*;
import org.springframework.social.connect.ConnectionData;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserConnection{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int connectionId;
    private String providerId;
    private String providerUserId;
    private String displayName;
    private String profileUrl;
    private String imageUrl;
    private String accessToken;
    private String secret;
    private String refreshToken;
    private Long expireTime;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    public User user;

    public UserConnection(ConnectionData data) {
        this.providerId = data.getProviderId();
        this.providerUserId = data.getProviderUserId();
        this.displayName = data.getDisplayName();
        this.profileUrl = data.getProfileUrl();
        this.imageUrl = data.getImageUrl();
        this.accessToken = data.getAccessToken();
        this.secret = data.getSecret();
        this.refreshToken = data.getRefreshToken();
        this.expireTime = data.getExpireTime();
    }
}
