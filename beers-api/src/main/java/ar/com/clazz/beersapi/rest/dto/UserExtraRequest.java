package ar.com.clazz.beersapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author Cesar Vargas
 *
 */
@Data
public class UserExtraRequest {

    @ApiModelProperty(example = "avatar")
    private String avatar;

}
