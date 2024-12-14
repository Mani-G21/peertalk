package chatapplication;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLHandler {


    public static String createXML(String from, String to, String contentType, String content) {
        return "<message>" +
                "<from>" + from + "</from>" +
                "<to>" + to + "</to>" +
                "<contentType>" + contentType + "</contentType>" +
                "<content>" + content + "</content>" +
                "</message>";
    }

    public static String extractTag(String xmlMessage, String tagName) {
        String regex = "<" + tagName + ">(.*?)</" + tagName + ">";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(xmlMessage);
        
        if (matcher.find()) {
            return matcher.group(1); 
        }
        
        return null;
    }
}
