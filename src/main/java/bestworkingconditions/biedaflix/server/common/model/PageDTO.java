package bestworkingconditions.biedaflix.server.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@NoArgsConstructor
public class PageDTO<T> {

    List<T> content;
    private Integer totalPages;
    private Long totalElements;
    private Boolean first;
    private Boolean last;
    private Boolean sorted;
    private Integer number;
    private Integer numberOfElements;
    private Integer size;
    private URI next;
    private URI previous;
}
