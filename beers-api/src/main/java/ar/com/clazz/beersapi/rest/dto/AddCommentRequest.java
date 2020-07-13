package ar.com.clazz.beersapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
/**
 * @author Cesar Vargas
 *
 */
@Data
public class AddCommentRequest {

    @ApiModelProperty(example = "Excelent")
    @NotBlank
    private String text;

}
