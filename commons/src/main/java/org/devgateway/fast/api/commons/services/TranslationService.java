package org.devgateway.fast.api.commons.services;


import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TranslationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${google.cloud.key}")
    String key;

    @Value("${google.translation.enable}")
    Boolean enable;

    public String t(String source, String locale) {
        List<TranslationsResource> values = t(Arrays.asList(source), locale);
        if (values.size() > 0) {
            return values.iterator().next().getTranslatedText();
        }
        return null;
    }

    public List<TranslationsResource> t(List<String> source, String locale) {
        try {
            if (enable) {
                logger.info("Calling google cloud translations, this operation may produce billing charges ");
                // See comments on
                //   https://developers.google.com/resources/api-libraries/documentation/translate/v2/java/latest/
                // on options to set
                Translate t = new Translate.Builder(
                        com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport()
                        , com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance(), null)
                        //Need to update this to your App-Name
                        .setApplicationName("COVD")
                        .build();

                Translate.Translations.List list = t.new Translations().list(
                        source,
                        locale);

                list.setKey(key);
                list.setFormat("text");
                TranslationsListResponse response = list.execute();

                return response.getTranslations();
            } else {
                logger.warn("Translation are disabled!!");
                return new ArrayList<TranslationsResource>();
            }

        } catch (Exception e) {
            logger.error("Error when translating values");
        }
        return null;
    }


}
