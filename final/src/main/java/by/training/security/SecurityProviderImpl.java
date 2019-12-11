package by.training.security;

import by.training.command.ActionCommand;
import by.training.core.Bean;

import java.util.EnumMap;

@Bean
public class SecurityProviderImpl implements SecurityProvider {

    private final EnumMap<AccessAllowedForType, SecurityDirector> registered;

    public SecurityProviderImpl() {
        this.registered = new EnumMap<>(AccessAllowedForType.class);
    }

    @Override
    public SecurityDirector get(ActionCommand command) {
        AccessAllowedForType securityType = command.getType().getSecurityType();
        return registered.get(securityType);
    }

    @Override
    public void register(SecurityDirector... directors) {
        for (SecurityDirector director : directors) {
            registered.put(director.getType(), director);
        }
    }

}