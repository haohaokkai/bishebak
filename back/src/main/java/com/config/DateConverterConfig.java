package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换器配置
 */
@Configuration
public class DateConverterConfig {

    /**
     * String -> LocalDate 转换器
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if (source == null || source.isEmpty()) {
                    return null;
                }
                
                // 尝试标准格式
                try {
                    return LocalDate.parse(source);
                } catch (Exception e) {
                    // 尝试自定义格式
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        return LocalDate.parse(source, formatter);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("无法解析日期: " + source, ex);
                    }
                }
            }
        };
    }
} 