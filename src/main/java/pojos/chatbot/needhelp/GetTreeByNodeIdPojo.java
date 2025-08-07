package pojos.chatbot.needhelp;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GetTreeByNodeIdPojo {
    public int nodeId;
    public String categoryName;
    public String categoryDescription;
    public int level;
    public ArrayList<NeedHelpChildCategoryList> needHelpChildCategoryList;
    public Object parent;
    public Media media;
    public UserInput userInput;
    public boolean raiseQuery;
    public int displayOrder;
    public String categoryLogo;
    public boolean leaf;
    public boolean root;

    /**
     * Error
     * */
    public int status;
    public String message;
    public String errorCode;
    public String statusCode;
}
