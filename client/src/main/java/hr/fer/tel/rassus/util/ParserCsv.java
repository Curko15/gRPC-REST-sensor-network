package hr.fer.tel.rassus.util;

import com.opencsv.exceptions.CsvValidationException;
import hr.fer.tel.rassus.dto.ReadingData;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.opencsv.CSVReader;



public class ParserCsv {
    public List<ReadingData> parseCsv(String filePath) throws IOException, CsvValidationException {
        List<ReadingData> readings = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            csvReader.readNext();

            while ((values = csvReader.readNext()) != null) {
                double temp = Double.parseDouble(values[0]);
                double pressure = Double.parseDouble(values[1]);
                double humidity = Double.parseDouble(values[2]);
                Double co = values[3].isEmpty() ? 0.0 : Double.parseDouble(values[3]);
                Double no2 = values[4].isEmpty() ? 0.0 : Double.parseDouble(values[4]);
                Double so2 = values[5].isEmpty() ? 0.0 : Double.parseDouble(values[5]);

                ReadingData reading = new ReadingData(temp, pressure, humidity, co, no2, so2);
                readings.add(reading);
            }
        }

        return readings;
    }
}
