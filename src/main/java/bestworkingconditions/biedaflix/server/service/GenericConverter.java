package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.BaseEntity;
import org.modelmapper.ModelMapper;

public abstract class GenericConverter<T extends BaseEntity> {

    protected ModelMapper mapper;

    public GenericConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
