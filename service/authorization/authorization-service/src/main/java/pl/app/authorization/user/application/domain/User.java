package pl.app.authorization.user.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.app.authorization.user.application.domain.event.UserCreatedEvent;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.event.DomainEventPublisher;

import java.util.*;
import java.util.regex.Pattern;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(name = "uc_t_user_email", columnNames = {"email"})
})
public class User extends BaseJpaAuditDomainAggregateRoot<User> {
    @Column(nullable = false)
    private String email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "user_name")
    private String username;
    @Column(name = "avatar_file_id")
    private UUID avatarFileId;
    @Column(nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private final Set<UserHasRole> roles = new LinkedHashSet<>();

    @Transient
    private PasswordEncoder passwordEncoder;

    private final static Integer MINIMAL_PASSWORD_LENGTH = 8;

    @SuppressWarnings("unused")
    protected User() {
        super();
    }

    public User(String email, String rawPassword, List<AggregateId> roles, DomainEventPublisher eventPublisher, PasswordEncoder passwordEncoder) {
        super(eventPublisher);
        this.passwordEncoder = passwordEncoder;

        setEmail(email);
        setPassword(rawPassword);
        setRoles(roles);
        this.eventPublisher.publish(new UserCreatedEvent(this.getId(), this.email));
    }

    // EMAIL
    public void setEmail(String email) {
        // VALIDATION
        if (email == null
                || !Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches()) {
            throw new UserException.EmailValidationException();
        }
        this.email = email;
    }

    // PASSWORD
    public void setPassword(String rawPassword) {
        // VALIDATION
        // Have eight characters or more and contains: capital letter, lowercase letter, one digit, one special symbol
        if (rawPassword == null
                || rawPassword.length() < MINIMAL_PASSWORD_LENGTH
                || !Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", Pattern.CASE_INSENSITIVE).matcher(rawPassword).matches()) {
            throw new UserException.PasswordValidationException();
        }
        this.password = passwordEncoder.encode(rawPassword);
    }

    public void changePassword(String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, this.password)) {
            throw new UserException.PasswordValidationException();
        }
        setPassword(newPassword);
    }

    // ROLES
    public void setRoles(Collection<AggregateId> newRoles) {
        this.roles.forEach(e -> removeRole(e.getRole()));
        newRoles.forEach(this::addRole);
    }

    public void addRole(AggregateId role) {
        if (getRole(role).isPresent()) {
            return;
        }
        this.roles.add(new UserHasRole(this, role));
    }

    public void removeRole(AggregateId role) {
        getRole(role)
                .ifPresent(e -> {
                    e.setUser(null);
                    this.roles.remove(e);
                });
    }

    public Optional<UserHasRole> getRole(AggregateId role) {
        return this.roles.stream()
                .filter(e -> Objects.equals(e.getRole(), role))
                .findAny();
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setAvatar(UUID avatarFileId) {
        this.avatarFileId = avatarFileId;
    }
}
