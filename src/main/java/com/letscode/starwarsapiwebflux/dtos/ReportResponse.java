package com.letscode.starwarsapiwebflux.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {
    private Double traitorsPercentage;
    private Double rebelPercentage;
    private ResourceAverageResponse resourceAverage;
    private Integer lostPoints;
}
