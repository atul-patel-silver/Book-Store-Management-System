package com.book.store.payload;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse {


    private String message;

    private boolean success;
}
