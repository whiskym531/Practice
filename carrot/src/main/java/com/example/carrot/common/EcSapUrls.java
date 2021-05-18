package com.example.carrot.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ec-sap-urls")
public class EcSapUrls {
    private String syncUrl;
}
