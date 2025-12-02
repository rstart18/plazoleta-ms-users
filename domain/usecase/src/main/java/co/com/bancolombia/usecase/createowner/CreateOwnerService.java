package co.com.bancolombia.usecase.createowner;

import co.com.bancolombia.model.user.User;

public interface CreateOwnerService {
    User createOwner(User request);
}