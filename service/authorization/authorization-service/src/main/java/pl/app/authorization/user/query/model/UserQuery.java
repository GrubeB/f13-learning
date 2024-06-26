package pl.app.authorization.user.query.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;

import java.util.Set;
import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class UserQuery extends BaseAuditEntity<UserQuery, UUID> {
    @Id
    private UUID id;
    private String email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "user_name")
    private String username;
    @Column(name = "avatar_file_id")
    private UUID avatarFileId;
    private String password;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserHasRoleQuery> roles;
}
