package by.training.security;

public enum SecurityType {
    ALL(ForAllAccessChecker.class),
    ONLY_ANONYMOUS(ForAnonymousAccessChecker.class),
    NON_ORGANIZER_USERS(ForNonOrganizerAccessChecker.class),
    NON_PLAYER_USERS(ForNonPlayerAccessChecker.class),
    USER(ForUserAccessChecker.class),
    ORGANIZER(ForOrganizerAccessChecker.class),
    PLAYER(ForPlayerAccessChecker.class),
    ADMIN(ForAdminAccessChecker.class);
    
    Clazz<?> clazz;
    
    SecurityType(Class<?> clazz) {
        this.clazz = clazz;
    }
}
