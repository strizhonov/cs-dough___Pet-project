package by.training.resourse;

public class ValidationRegexp {

    public static final String EMAIL_REGEXP = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String PASSWORD_REGEXP = "^[\\w!@#$%^&]{6,45}$";
    public static final String USERNAME_REGEXP = "^[A-Za-z0-9А-Яа-я_\\-]{1,45}$";
    public static final String TOURNAMENT_NAME_REGEXP = "^[A-Za-z0-9А-Яа-я\u0020_\\-]{1,45}$";
    public static final String ORGANIZER_NAME_REGEXP = "^[A-Za-z0-9А-Яа-я\u0020_\\-]{1,45}$";
    public static final String NICKNAME_REGEXP = "^[A-Za-z0-9А-Яа-я_\\-]{1,45}$";
    public static final String PLAYER_NAME_REGEXP = "^[A-Za-zА-Яа-я\u0020\\-]{1,45}$";
    public static final String PLAYER_SURNAME_REGEXP = "^[A-Za-zА-Яа-я\\-]{1,45}$";

    /**
     * "blank" value is for empty image field
     */
    public static final String IMG_REGEXP = "image/png|image/gif|image/jpeg|" + AttributesContainer.BLANK.toString();

}
