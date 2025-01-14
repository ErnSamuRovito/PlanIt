package model.utils;

import java.util.regex.Pattern;

public class FormatValidator
{
    private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email)
    {
        if (email == null || email.isEmpty()) {return false;}
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPassword(String password){
        return (password!=null && password.length() >= 6);
    }
}

