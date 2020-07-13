package ar.com.clazz.beersapi.mapper;

import java.util.Optional;

import ar.com.clazz.beersapi.model.Beer;
import ar.com.clazz.beersapi.model.UserExtra;
import ar.com.clazz.beersapi.rest.dto.CreateBeerRequest;
import ar.com.clazz.beersapi.rest.dto.BeerDto;
import ar.com.clazz.beersapi.rest.dto.UpdateBeerRequest;
import ar.com.clazz.beersapi.service.UserExtraService;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cesar Vargas
 *
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserExtraService.class}
)
public abstract class BeerMapper {

  @Autowired
  private UserExtraService userExtraService;
  
  public abstract Beer toBeer(CreateBeerRequest createBeerRequest);

  public abstract void updateBeerFromDto(UpdateBeerRequest updateBeerRequest, @MappingTarget Beer beer);

  public abstract BeerDto toBeerDto(Beer beer);

  public BeerDto.CommentDto toBeerDtoCommentDto(Beer.Comment comment) {
    BeerDto.CommentDto commentDto = new BeerDto.CommentDto();
    commentDto.setUsername(comment.getUsername());
    commentDto.setText(comment.getText());
    commentDto.setTimestamp(comment.getTimestamp());
    
    Optional<UserExtra> userExtra = userExtraService.getUserExtra(comment.getUsername());
    commentDto.setAvatar(userExtra.isPresent() ? userExtra.get().getAvatar() : comment.getUsername());
    return commentDto;
  }

}