package com.webtv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility for translate a message.
 * 
 * @author abned.fandresena
 *          Apr 26, 2020
 */
@Component
public class Translator {

    @Autowired
    private MessageSource messageSource;

    /**
     * Get tranlated message by key.
     * @param key The key message
     * @return java.lang.String
     */
    public String get(String key) {
        System.out.println("LANG: "+LocaleContextHolder.getLocale().getLanguage());
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    /**
     * Get the translated message by key with data
     * @param key  The message key
     * @param args The translator data
     * @return
     */
    public String get(String key, String ...args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}