package co.com.bancolombia.adapter;

import co.com.bancolombia.dto.response.RestaurantApiResponse;
import co.com.bancolombia.model.restaurant.gateways.RestaurantGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RestaurantGatewayImpl implements RestaurantGateway {

    private static final String OWNER_VALIDATION_ENDPOINT = "/api/restaurants/%d/owner/%d";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final RestTemplate restTemplate;
    private final String restaurantServiceUrl;

    public RestaurantGatewayImpl(RestTemplate restTemplate,
                                 @Value("${services.foodcourt.url}") String restaurantServiceUrl) {
        this.restTemplate = restTemplate;
        this.restaurantServiceUrl = restaurantServiceUrl;
    }

    @Override
    public boolean isOwnerOfRestaurant(Long restaurantId, Long ownerId, String authToken) {
        try {
            String url = restaurantServiceUrl + String.format(OWNER_VALIDATION_ENDPOINT, restaurantId, ownerId);
            log.info("Calling restaurant service: {}", url);

            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTHORIZATION_HEADER, authToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<RestaurantApiResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, RestaurantApiResponse.class);

            RestaurantApiResponse apiResponse = response.getBody();

            if (apiResponse != null && apiResponse.getData() != null) {
                log.info("Is owner of restaurant: {}", apiResponse.getData().isOwner());
                return apiResponse.getData().isOwner();
            }

            return false;
        } catch (Exception e) {
            log.error("Error validating restaurant ownership: {}", e.getMessage());
            return false;
        }
    }
}