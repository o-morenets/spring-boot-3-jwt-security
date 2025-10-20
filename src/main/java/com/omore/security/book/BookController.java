package com.omore.security.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books")
public class BookController {

    private final BookService service;

    @Operation(
            summary = "POST endpoint for any authenticated user",
            description = "Permissions: `any authenticated`",
            responses = {
                    @ApiResponse(
                            description = "Accepted",
                            responseCode = "202"
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
    public ResponseEntity<?> save(@RequestBody BookRequest request) {
        service.save(request);

        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "GET endpoint for any authenticated user",
            description = "Permissions: `any authenticated`",
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
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(service.findAll());
    }
}
