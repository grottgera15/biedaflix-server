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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class PageMapper {

    @SneakyThrows
    private <T> void setLinks(PageDTO<T> pageDTO, HttpServletRequest request){

        if(!pageDTO.getFirst()){
           // Map<String, String[]> params = request.getParameterMap();
            MultiValueMap<String,String> params = new LinkedMultiValueMap<String,String>();

            for ( Map.Entry<String,String[]> entry : request.getParameterMap().entrySet() ){

            }

            params.put("page", Collections.singletonList(String.valueOf(pageDTO.getNumber() - 1)));

            UriComponents components = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString()).queryParams(params).build();

            pageDTO.setPrevious(components.toUri());
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
