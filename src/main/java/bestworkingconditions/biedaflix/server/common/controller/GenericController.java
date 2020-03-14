package bestworkingconditions.biedaflix.server.common.controller;

import bestworkingconditions.biedaflix.server.common.model.BaseDTO;
import bestworkingconditions.biedaflix.server.common.model.BaseEntity;
import bestworkingconditions.biedaflix.server.common.service.GenericService;
import bestworkingconditions.biedaflix.server.common.util.ModelMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class GenericController<T extends BaseEntity,R extends BaseDTO,S extends GenericService<T,?>> {

    private final Class<T> Tclazz;
    private final Class<R> Rclazz;

    protected final S service;
    protected final ModelMapperUtils modelMapperUtils;
    protected ModelMapper mapper;

    public GenericController(Class<T> tclazz, Class<R> rclazz, S service, ModelMapperUtils modelMapperUtils) {
        this.Tclazz = tclazz;
        this.Rclazz = rclazz;
        this.service = service;
        this.modelMapperUtils = modelMapperUtils;
        this.mapper = modelMapperUtils.getModelMapper();
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        List<R> response = modelMapperUtils.mapAll(service.getAll(),Rclazz);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        return new ResponseEntity<>(mapper.map(service.findById(id),Rclazz), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
