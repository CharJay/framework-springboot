package com.framework.core.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.framework.core.utils.date.DateFormatHelper;

@Component
public class MvcConfig {
    
    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat(DateFormatHelper.FORMAT_YEAR_SECOND_CONCAT);
                Date date = null;
                    try {
                        date = sdf.parse( source );
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                return date;
            }
        };
    }
    
    
}
