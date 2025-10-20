package com.omore.security.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management")
public class ManagementController {

    @Operation(
            summary = "POST endpoint for manager",
            description = "Permissions: `manager:read` or `admin:read`",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping
    public String post() {
        return "POST:: management controller";
    }

    @Operation(
            summary = "GET endpoint for manager",
            description = "Permissions: `manager:read` or `admin:read`",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping
    public String get() {
        return "GET:: management controller";
    }

    @Operation(
            summary = "PUT endpoint for manager",
            description = "Permissions: `manager:read` or `admin:read`",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403"
                    )
            }
    )
    @PutMapping
    public String put() {
        return "PUT:: management controller";
    }

    @Operation(
            summary = "DELETE endpoint for manager",
            description = "Permissions: `manager:read` or `admin:read`",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403"
                    )
            }
    )
    @DeleteMapping
    public String delete() {
        return "DELETE:: management controller";
    }
}
