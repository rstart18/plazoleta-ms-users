package co.com.bancolombia.model.restaurant.gateways;

public interface RestaurantGateway {
    boolean isOwnerOfRestaurant(Long restaurantId, Long ownerId, String authToken);
}
