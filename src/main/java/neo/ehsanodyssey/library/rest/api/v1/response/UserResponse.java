package neo.ehsanodyssey.library.rest.api.v1.response;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import neo.ehsanodyssey.library.service.dto.LibraryDto;

import java.util.List;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class UserResponse {
    private String id;
    private String username;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @Singular
    @JsonIgnore
    @ToString.Exclude
    private List<LibraryDto> libraries;
}
