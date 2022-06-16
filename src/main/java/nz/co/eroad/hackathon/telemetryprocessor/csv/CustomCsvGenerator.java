package nz.co.eroad.hackathon.telemetryprocessor.csv;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import nz.co.eroad.hackathon.telemetryprocessor.gps.GpsTelemetry;
import nz.co.eroad.hackathon.telemetryprocessor.sensor.SensorTelemetry;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Component
@Slf4j
public class CustomCsvGenerator {

    public String generate(GpsTelemetry gpsTelemetry) throws IOException {
        try (StringWriter writer = new StringWriter(); CSVPrinter csvPrinter = CSVFormat.DEFAULT
                .withHeader("time (ms)", "bearing (deg)", "lat (deg)", "lon (deg)", "alt (m)", "speed (m/s)")
                .print(writer)) {
            Instant start = Instant.ofEpochMilli(gpsTelemetry.getStart().longValue());

            gpsTelemetry.getLogs()
                    .stream()
                    .forEach(gpsData ->{
                        Instant timestamp = Instant.ofEpochMilli(gpsData.getTimestamp().longValue());
                        long time = Duration.between(start, timestamp).toMillis();
                        BigDecimal bearing = gpsData.getBearing();
                        BigDecimal latitude = gpsData.getLat();
                        BigDecimal longitude = gpsData.getLon();
                        BigDecimal speed = gpsData.getSpeed();
                        BigDecimal altitude = BigDecimal.ZERO;

                        Try.run(() -> csvPrinter.printRecord(time, bearing, latitude, longitude, altitude, speed));
                    });

            String csv = writer.toString();
            log.debug("CSV: " + csv);
            return csv;
        }
    }

    public String generate(SensorTelemetry sensorTelemetry) throws IOException {
        try (StringWriter writer = new StringWriter(); CSVPrinter csvPrinter = CSVFormat.DEFAULT
                .withHeader("time (ms)", "accel x (m/s²)", "accel y (m/s²)", "accel z (m/s²)")
                .print(writer)) {
            Instant start = Instant.ofEpochMilli(sensorTelemetry.getStart().longValue());

            sensorTelemetry.getSensorData()
                    .stream()
                    .forEach(sensorData ->{
                        Instant timestamp = Instant.ofEpochMilli(sensorData.getT_sec().longValue());
                        long time = Duration.between(start, timestamp).toMillis();
                        BigDecimal xAcceleration = sensorData.getX_acc().negate();
                        BigDecimal yAcceleration = sensorData.getY_acc();
                        BigDecimal zAcceleration = sensorData.getZ_acc();

                        Try.run(() -> csvPrinter.printRecord(time, xAcceleration, yAcceleration, zAcceleration));
                    });

            String csv = writer.toString();
            log.debug("CSV: " + csv);
            return csv;
        }
    }
}
