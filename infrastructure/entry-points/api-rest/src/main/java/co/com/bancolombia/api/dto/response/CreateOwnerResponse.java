package co.com.bancolombia.api.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOwnerResponse {
    private Long id;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
}