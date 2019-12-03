package by.training.security;

import by.training.appentry.Bean;
import by.training.command.ActionCommand;

import java.util.EnumMap;

@Bean
public class SecurityProviderImpl implements SecurityProvider<AccessAllowedForType> {

    private EnumMap<AccessAllowedForType, SecurityDirector<AccessAllowedForType>> registered;

    public SecurityProviderImpl() {
        this.registered = new EnumMap<>(AccessAllowedForType.class);
    }

    @Override
    public SecurityDirector<AccessAllowedForType> get(ActionCommand command) {
        AccessAllowedForType securityType = command.getType().getSecurityType();
        return registered.get(securityType);
    }

    @Override
    public void register(SecurityDirector<AccessAllowedForType> supervisor) {
        registered.put(supervisor.getType(), supervisor);
    }

}