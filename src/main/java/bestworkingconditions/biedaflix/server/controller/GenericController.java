package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.BaseDTO;
import bestworkingconditions.biedaflix.server.model.BaseEntity;
import bestworkingconditions.biedaflix.server.service.GenericService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class GenericController<T extends BaseEntity,R extends BaseDTO,S extends GenericService<T,?>> {

    private final Class<T> Tclazz;
    private final Class<R> Rclazz;
    private final S service;
    protected final ModelMapper mapper;

    public GenericController(Class<T> tclazz, Class<R> rclazz, S service, ModelMapper mapper) {
        this.Tclazz = tclazz;
        this.Rclazz = rclazz;
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll(){

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        return new ResponseEntity<>(mapper.map(service.findById(id),Rclazz), HttpStatus.OK);
    }

}
