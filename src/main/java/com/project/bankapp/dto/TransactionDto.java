package com.project.bankapp.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) class representing transaction information.
 */
@Data
@Builder
public class TransactionDto {
    private String debitAccountUuid;
    private String creditAccountUuid;
    private String type;
    private String currencyCode;
    private BigDecimal amount;
    private String description;
}
