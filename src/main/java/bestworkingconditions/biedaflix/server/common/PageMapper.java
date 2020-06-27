package bestworkingconditions.biedaflix.server.common;

import bestworkingconditions.biedaflix.server.common.model.PageDTO;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class PageMapper {

    private URI getPageUriWithChangedPageNumber(Integer page, HttpServletRequest request){
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

        for ( Map.Entry<String,String[]> entry : request.getParameterMap().entrySet() ){
            params.put(entry.getKey(), Arrays.asList(entry.getValue()));
        }

        params.put("page", Collections.singletonList(String.valueOf(page)));

        UriComponents components = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString()).queryParams(params).build();

        return components.toUri();
    }

    @SneakyThrows
    private <T> void setLinks(PageDTO<T> pageDTO, HttpServletRequest request){

        if(!pageDTO.getFirst()){
            pageDTO.setPrevious(getPageUriWithChangedPageNumber(pageDTO.getNumber() - 1,request));
        }

        if(!pageDTO.getLast()){
            pageDTO.setPrevious(getPageUriWithChangedPageNumber(pageDTO.getNumber() + 1,request));
        }
    }

    public <T,U> PageDTO<U> pageDTOFromPage(Page<T> page, Function<List<T>, List<U>> mapper, HttpServletRequest request){
        PageDTO<U> pageDTO = new PageDTO<U>();
        pageDTO.setFirst(page.isFirst());
        pageDTO.setLast(page.isLast());
        pageDTO.setNumber(page.getNumber());
        pageDTO.setNumberOfElements(page.getNumberOfElements());
        pageDTO.setSize(page.getSize());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setTotalPages(page.getTotalPages());

        pageDTO.setContent(mapper.apply(page.getContent()));
        setLinks(pageDTO,request);
        return pageDTO;
    }
}
