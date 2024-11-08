package model;

import java.util.regex.Pattern;

public class EmailValidator
{
    private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValid(String email)
    {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
