package nz.co.eroad.hackathon.telemetryprocessor.gps;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@AllArgsConstructor
public class GpsUploadController {

    private GpsProcessor gpsProcessor;

    @PostMapping(value = "/upload/gps", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "text/csv")
    @ResponseBody
    public String uploadGpsTelemetry(@RequestParam("file") MultipartFile file) throws IOException {
        String csvFile = gpsProcessor.process(file);
        log.debug("CSV File: " + csvFile);

        return csvFile;
    }
}
