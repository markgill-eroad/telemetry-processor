package nz.co.eroad.hackathon.telemetryprocessor.sensor;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nz.co.eroad.hackathon.telemetryprocessor.csv.CustomCsvGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Slf4j
@Component
@AllArgsConstructor
public class SensorProcessor {

    private CustomCsvGenerator customCsvGenerator;

    public String process(MultipartFile file) throws IOException {
        Reader reader = new InputStreamReader(file.getInputStream());
        SensorTelemetry sensorTelemetry = new Gson().fromJson(reader, SensorTelemetry.class);

        log.debug("File: " + sensorTelemetry);

        return customCsvGenerator.generate(sensorTelemetry);
    }
}
