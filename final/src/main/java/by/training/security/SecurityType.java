package by.training.security;

public enum SecurityType {
    ANY(ForAnyAccessManager.class),
    ANONYMOUS(ForAnonymousAccessManager.class),
    NON_ORGANIZER_USERS(ForNonOrganizerAccessManager.class),
    NON_PLAYER_USERS(ForNonPlayerAccessManager.class),
    USER_OWNER(ForUserOwnerAccessManager.class),
    ORGANIZER_OWNER(ForOrganizerOwnerAccessManager.class),
    PLAYER_OWNER(ForPlayerOwnerAccessManager.class),
    ANY_PLAYER(ForAnyPlayerAccessManager.class),
    ANY_ORGANIZER(ForAnyOrganizerAccessManager.class),
    ADMIN(ForAdminAccessManager.class);
    
    Clazz<?> clazz;
    
    SecurityType(Class<?> clazz) {
        this.clazz = clazz;
    }
    
    public Class<?> getAccessManagerClass() {
        return clazz;
    }
}
