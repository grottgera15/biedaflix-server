package bestworkingconditions.biedaflix.server.conversion;

import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;

public abstract class ConverterConfigurator {

    protected final ModelMapper modelMapper;

    protected ConverterConfigurator(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    @PostConstruct
    protected abstract void configure();
}
