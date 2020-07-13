package ar.com.clazz.beersapi.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
/**
 * @author Cesar Vargas
 *
 */
@Data
public class BeerDto {

    private String id;
    private String name;
    private String description;
    private String tagline;
    private String image_url;
    private List<CommentDto> comments;

    @Data
    public static class CommentDto {
        private String username;
        private String avatar;
        private String text;
        private LocalDateTime timestamp;
    }

}