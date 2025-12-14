package co.com.bancolombia.usecase.owner;

import co.com.bancolombia.model.user.User;

public interface OwnerService {
    User createOwner(User request, String userRole);
}