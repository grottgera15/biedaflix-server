package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "streamingServices")
public class StreamingServiceSource extends FileResource{

    @Id
    private String id;
    private String name;

    public StreamingServiceSource() {
    }

    public StreamingServiceSource(String extension, String name) {
        super(extension);
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String getFilePath() {
        return  "/" + getClass().getSimpleName() + "/" + name + "." + getFileExtension() ;
    }
}
