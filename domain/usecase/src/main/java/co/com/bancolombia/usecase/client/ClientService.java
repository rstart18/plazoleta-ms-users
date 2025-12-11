package co.com.bancolombia.usecase.client;

import co.com.bancolombia.model.user.User;

public interface ClientService {
    User createClient(User user);
}
