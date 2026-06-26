package com.college.teamcollab.dto.channel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateChannelRequest {
    @NotBlank(message = "Channel name is required")
    @Size(min = 6, max = 50, message = "Channel name must be between 2 and 50 characters")
    private String name;

    @Size(max = 200,message = "Description cannot exceed 200 characters")
    private String description;
}
