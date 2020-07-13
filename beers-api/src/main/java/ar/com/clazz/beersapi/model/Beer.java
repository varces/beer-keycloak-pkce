package ar.com.clazz.beersapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Cesar Vargas
 *
 */
@Data
@Document(collection = "beer")
public class Beer {

    @Id
    private String id;
    private String name;
    private String description;
    private String tagline;
    private String image_url;
    private List<Comment> comments = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class Comment {
        private String username;
        private String text;
        private LocalDateTime timestamp;
    }

}	