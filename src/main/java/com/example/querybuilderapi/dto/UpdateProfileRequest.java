package com.example.querybuilderapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for updating the user's profile info.
 */
public class UpdateProfileRequest {

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 _-]+$", message = "Display name can only contain letters, numbers, spaces, underscores, and hyphens")
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
