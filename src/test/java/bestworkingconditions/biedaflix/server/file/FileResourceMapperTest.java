package bestworkingconditions.biedaflix.server.file;

import bestworkingconditions.biedaflix.server.common.properties.AppProperties;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

class FileResourceMapperTest {

    @Mock
    AppProperties appProperties;

    @Mock
    StoreProperties storeProperties;

    FileResourceMapper fileResourceMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        fileResourceMapper = new FileResourceMapper(appProperties,storeProperties);
    }

    @Test
    void mapToURL() {

        when(storeProperties.getPath()).thenReturn("files/");
        when(appProperties.getApiDomain()).thenReturn("http://api.example.com/");


        FileResource testResource = new FileResource();
        testResource.setId("5e72266ebcca2e275fa3f1e4");

        Assert.assertEquals("http://api.example.com/files/5e72266ebcca2e275fa3f1e4",fileResourceMapper.mapToURL(testResource).toString());
    }
}