package nz.co.eroad.hackathon.telemetryprocessor.sensor;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class SensorUploadController {

    private SensorProcessor sensorProcessor;

    @PostMapping(value = "/upload/sensor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "text/csv")
    @ResponseBody
    public String uploadGpsTelemetry(@RequestParam("file") MultipartFile file) throws IOException {
        return sensorProcessor.process(file);
    }
}
