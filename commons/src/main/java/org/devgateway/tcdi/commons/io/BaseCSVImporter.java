package org.devgateway.tcdi.commons.io;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public abstract class BaseCSVImporter<T> extends BaseImport<T, String[]> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public void start(String path, String format) {
        beforeStart();
        File srcFile = new File(path);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
        Date importDate = new Date();
        String[] values = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8));
            CSVReader reader = new CSVReader(in);
            reader.skip(1);
            final Integer[] count = new Integer[1];
            count[0] = 0;
            while ((values = reader.readNext()) != null) {

                T record = read(values);
                if (record != null) {
                    logger.info("Record counts " + (count[0]++));
                    save(record);
                }
            }

        } catch (Exception e) {
            logger.error("Error when reading csv file", e);
        }


    }
}
