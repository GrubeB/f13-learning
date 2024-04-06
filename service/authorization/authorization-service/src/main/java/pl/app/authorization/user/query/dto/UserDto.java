package pl.app.authorization.user.query.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements
        Serializable {
    private UUID id;
    private String email;
    private String fullName;
    private String username;
    private UUID avatarFileId;
    private List<String> roles;
    private List<String> permissions;
}
