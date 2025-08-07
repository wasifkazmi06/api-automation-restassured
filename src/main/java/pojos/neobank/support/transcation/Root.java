package pojos.neobank.support.transcation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Root {
    public Object exception;
    public Pagination pagination;
    public List<Result> result;
}
