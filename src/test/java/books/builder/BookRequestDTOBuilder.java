package books.builder;

import com.leonardo.bookstoremanager.dto.BookRequestDTO;
import com.leonardo.bookstoremanager.dto.UserDTO;
import com.leonardo.bookstoremanager.users.builder.UserDTOBuilder;
import lombok.Builder;

@Builder
public class BookRequestDTOBuilder {

    @Builder.Default
    private Long id =  1L;

    @Builder.Default
    private String name = "Book Store Manager";

    @Builder.Default
    private String isbn = "978-3-16-148410-0";

    @Builder.Default
    private Integer pages = 200;

    @Builder.Default
    private Integer chapters = 10;

    @Builder.Default
    private Long authorId = 3L;

    @Builder.Default
    private Long publisherId = 2L;

    @Builder.Default
    private UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookRequestDTO buildRequestBookDTO(){
        return new BookRequestDTO(
                id,
                name,
                isbn,
                pages,
                chapters,
                authorId,
                publisherId
        );
    }
}
