package base.entiity;

import lombok.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private ObjectId userId;
    private String username;
    private String password;
    private Role role;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
}
