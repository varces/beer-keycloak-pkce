package ar.com.clazz.beersapi.rest.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author Cesar Vargas
 *
 */
@Data
public class UpdateBeerRequest {

    @ApiModelProperty(position = 1, example = "Buzz")
    @NotBlank
    private String name;
    @ApiModelProperty(position = 2, example = "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.")
    @NotBlank
    private String description;
    @ApiModelProperty(position = 3, example = "A Real Bitter Experience.")
    @NotBlank
    private String tagline;

    @ApiModelProperty(position = 4, example = "https://images.punkapi.com/v2/keg.png")
    private String image_url;

}