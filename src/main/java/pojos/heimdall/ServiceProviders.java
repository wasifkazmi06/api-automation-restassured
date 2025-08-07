package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServiceProviders {
    private String name;
    private String code;
    private ArrayList<Fields> fields;

}
