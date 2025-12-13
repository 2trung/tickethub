package org.tickethub.controller.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tickethub.application.service.auth.AuthService;
import org.tickethub.controller.dto.auth.AuthResponse;
import org.tickethub.controller.dto.auth.LoginRequest;
import org.tickethub.controller.dto.auth.RefreshTokenRequest;
import org.tickethub.controller.dto.auth.RegisterRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication APIs for user registration, login, and token management")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register new user",
            description = "Register a new user account with email, phone number, and password. Returns access and refresh tokens upon successful registration."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "code": 1000,
                                        "message": "success",
                                        "data": {
                                            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                            "tokenType": "Bearer"
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Email or phone number already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "code": 1004,
                                        "message": "Email đã tồn tại"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class)
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<org.tickethub.infrastructure.dto.ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        log.info("POST /auth/register - Register new user with email: {}", request.getEmail());
        AuthResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(org.tickethub.infrastructure.dto.ApiResponse.<AuthResponse>builder()
                        .data(response)
                        .build());
    }

    @Operation(
            summary = "User login",
            description = "Authenticate user with email and password. Returns access and refresh tokens upon successful authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "code": 1000,
                                        "message": "success",
                                        "data": {
                                            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                            "tokenType": "Bearer"
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - Invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "code": 1006,
                                        "message": "Tài khoản hoặc mật khẩu không chính xác"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class)
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<org.tickethub.infrastructure.dto.ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        log.info("POST /auth/login - User login attempt with email: {}", request.getEmail());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(org.tickethub.infrastructure.dto.ApiResponse.<AuthResponse>builder()
                .data(response)
                .build());
    }

    @Operation(
            summary = "Refresh access token",
            description = "Get a new access token and refresh token using a valid refresh token. The old refresh token will be invalidated."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token refreshed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "code": 1000,
                                        "message": "success",
                                        "data": {
                                            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                            "tokenType": "Bearer"
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or expired refresh token",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "code": 1007,
                                        "message": "Token không hợp lệ"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.tickethub.infrastructure.dto.ApiResponse.class)
                    )
            )
    })
    @PostMapping("/refresh")
    public ResponseEntity<org.tickethub.infrastructure.dto.ApiResponse<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        log.info("POST /auth/refresh - Refresh token request");
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(org.tickethub.infrastructure.dto.ApiResponse.<AuthResponse>builder()
                .data(response)
                .build());
    }
}

