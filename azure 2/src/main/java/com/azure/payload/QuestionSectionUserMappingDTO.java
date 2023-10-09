package com.azure.payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSectionUserMappingDTO {
    private Long id;
    private String questionCode;
    private int index;
}