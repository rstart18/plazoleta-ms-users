package co.com.bancolombia.jpa.entity.employeerestaurant;

import co.com.bancolombia.model.employeerestaurant.EmployeeRestaurant;
import co.com.bancolombia.model.employeerestaurant.gateways.EmployeeRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeRestaurantJPARepositoryAdapter implements EmployeeRestaurantRepository {

    private final EmployeeRestaurantJPARepository repository;

    @Override
    public Optional<EmployeeRestaurant> findByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId)
                .map(this::toDomain);
    }

    @Override
    public EmployeeRestaurant create(EmployeeRestaurant employeeRestaurant) {
        EmployeeRestaurantEntity entity = toEntity(employeeRestaurant);
        EmployeeRestaurantEntity savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    private EmployeeRestaurant toDomain(EmployeeRestaurantEntity entity) {
        return EmployeeRestaurant.builder()
                .id(entity.getId())
                .employeeId(entity.getEmployeeId())
                .restaurantId(entity.getRestaurantId())
                .build();
    }

    private EmployeeRestaurantEntity toEntity(EmployeeRestaurant domain) {
        return new EmployeeRestaurantEntity(
                domain.getId(),
                domain.getEmployeeId(),
                domain.getRestaurantId()
        );
    }
}