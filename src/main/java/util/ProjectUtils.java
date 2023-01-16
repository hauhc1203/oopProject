package util;

import dao.ProductDAO;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectUtils {
    public static boolean validate(String regex,String str,int minlen,int maxlen){
        if ( str.length()>maxlen||str.length()<minlen)
            return false;
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        return matcher.matches();
    }
    public static boolean isExistedProductCode(String productCode) {
        List<String> productCodes = ProductDAO.getAllProductCode();

        if (productCodes.contains(productCode))
            return true;
        return false;
    }
}
