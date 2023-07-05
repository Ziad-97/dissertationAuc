package com.dissertationauc.dissertationauc.Auction.exception.dataExceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class GlobalErrorResponse {

    private String defaultErrorCode;

    private String globalErrorMessage;



}
