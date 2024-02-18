package neo.ehsanodyssey.library.rest.api.v1.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class UpdateBookRequest {
    @NotNull
    @NotBlank
    private String title;
    @Min(1)
    private int pages;
}
