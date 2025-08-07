package pojos.chatbot.needhelp;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class GetCategoryPojo {
    public int nodeId;
    public String categoryName;
    public String categoryDescription;
    public int level;
    public ArrayList<NeedHelpChildCategoryList> needHelpChildCategoryList;
    public Object parent;
    public Object media;
    public Object userInput;
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
}




