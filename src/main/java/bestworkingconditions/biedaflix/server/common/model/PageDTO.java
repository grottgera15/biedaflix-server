package bestworkingconditions.biedaflix.server.common.model;

import java.util.List;

public class PageDTO<T> {

    List<T> content;
    private Integer totalPages;
    private Integer totalElements;
    private Boolean first;
    private Boolean last;
    private Boolean sorted;
    private Integer number;
    private Integer numberOfElements;
    private Integer size;
}
