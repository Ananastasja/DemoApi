package requers_objects;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisteredUser {

    String email;
    String password;
}
