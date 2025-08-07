package pojos.chatbot.needhelp;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class NeedHelpChildCategoryList{
    public int nodeId;
    public String categoryName;
    public String categoryDescription;
    public int level;
    public ArrayList<Object> needHelpChildCategoryList;
    public int parent;
    public Object media;
    public Object userInput;
    public boolean raiseQuery;
    public int displayOrder;
    public String categoryLogo;
    public boolean leaf;
    public boolean root;
}