package pojos.chatbot.needhelp;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Ctum{
    public int id;
    public String text;
    public String textType;
    public String action;
    public int displayIndex;
    public ArrayList<String> ctaActionList;
}
