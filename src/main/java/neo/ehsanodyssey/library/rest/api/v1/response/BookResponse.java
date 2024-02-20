package neo.ehsanodyssey.library.rest.api.v1.response;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class BookResponse {
    private String id;
    @JsonProperty("isbn_13")
    private String isbn13;
    private String title;
    private int pages;
    @ToString.Exclude
    private List<LibraryResponse> libraries;
}
