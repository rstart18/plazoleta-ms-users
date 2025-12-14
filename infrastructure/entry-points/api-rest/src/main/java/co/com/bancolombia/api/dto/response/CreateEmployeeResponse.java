package co.com.bancolombia.api.dto.response;

import lombok.Data;

@Data
public class CreateEmployeeResponse {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String phone;
    private String email;
    private Long restaurantId;
}