package br.rmginner.utils;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ResponseError {

    private Map<String, String> errors;
}
