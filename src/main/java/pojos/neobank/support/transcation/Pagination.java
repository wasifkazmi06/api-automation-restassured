package pojos.neobank.support.transcation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {
    public int pageSize;
    public int pageNo;
    public int totalPages;
    public int totalElements;
    public boolean list;
}
