package bestworkingconditions.biedaflix.server.user;

import bestworkingconditions.biedaflix.server.conversion.ConverterConfigurator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverterConfigurator extends ConverterConfigurator {

    @Autowired
    protected UserConverterConfigurator(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void configure() {

    }
}