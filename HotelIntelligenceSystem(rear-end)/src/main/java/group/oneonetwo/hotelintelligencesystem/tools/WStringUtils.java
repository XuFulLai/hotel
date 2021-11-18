package group.oneonetwo.hotelintelligencesystem.tools;

/**
 * @author 文
 * @description 自定义字符串工具
 */
public class WStringUtils {

    public static boolean isBlank(String s) {
        if ("".equals(s) || s == null) {
            return true;
        }
        return false;
    }



}
