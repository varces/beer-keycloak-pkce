package ar.com.clazz.beersapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @author Cesar Vargas
 *
 */
@Data
@Document(collection = "userextras")
public class UserExtra {

    @Id
    private String username;
    private String avatar;

    public UserExtra(String username) {
        this.username = username;
    }
}
