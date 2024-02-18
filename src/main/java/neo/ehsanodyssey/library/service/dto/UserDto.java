package neo.ehsanodyssey.library.service.dto;

import lombok.*;

import java.io.Serializable;
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
public class UserDto implements Serializable {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    @Singular
    private List<LibraryDto> libraries;
}
