package org.tickethub.controller.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tickethub.controller.dto.user.UserResponse;
import org.tickethub.infrastructure.exception.AppException;
import org.tickethub.infrastructure.exception.ErrorCode;
import org.tickethub.infrastructure.persistence.entity.Role;
import org.tickethub.infrastructure.persistence.entity.User;
import org.tickethub.infrastructure.persistence.repository.UserRepository;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User", description = "User management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserRepository userRepository;

    @Operation(
            summary = "Get current user profile",
            description = "Retrieve the authenticated user's profile information including roles and personal details."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User profile retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "code": 1000,
                                        "message": "success",
                                        "data": {
                                            "id": "123e4567-e89b-12d3-a456-426614174000",
                                            "name": "John Doe",
                                            "email": "user@example.com",
                                            "phoneNumber": "0123456789",
                                            "dob": "1990-01-01",
                                            "avatar": null,
                                            "roles": ["USER"]
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing token",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "code": 1003,
                                        "message": "Chưa xác thực"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class)
                    )
            )
    })
    @GetMapping("/me")
    public ResponseEntity<org.tickethub.infrastructure.dto.ApiResponse<UserResponse>> getCurrentUser() {
        log.info("GET /user/me - Get current user profile");

        // Get authenticated user from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Find user by email (username)
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Map to response DTO
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dob(user.getDob())
                .avatar(user.getAvatar())
                .roles(user.getRoles() != null ?
                        user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet())
                        : null)
                .build();

        return ResponseEntity.ok(org.tickethub.infrastructure.dto.ApiResponse.<UserResponse>builder()
                .data(response)
                .build());
    }
}

