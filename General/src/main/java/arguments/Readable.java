package arguments;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.IODevice;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringPersonArguments.class, name = "StringPersonArguments"),
        @JsonSubTypes.Type(value = IntegerPersonArguments.class, name = "IntegerPersonArguments"),
        @JsonSubTypes.Type(value = KeyArguments.class, name = "KeyArguments"),
        @JsonSubTypes.Type(value = PersonArguments.class, name = "PersonArguments"),
        @JsonSubTypes.Type(value = NoReadableArguments.class, name = "NoReadableArguments"),
        @JsonSubTypes.Type(value = WeightArguments.class, name = "WeightArguments"),
        @JsonSubTypes.Type(value = LocationArguments.class, name = "LocationArguments"),
        @JsonSubTypes.Type(value = FileArguments.class, name = "FileArguments")
})
public interface Readable {
    ObjectMapper mapper = new ObjectMapper();
    String read(IODevice from) throws JsonProcessingException;
}
