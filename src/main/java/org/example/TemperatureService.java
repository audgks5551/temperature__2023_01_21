package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TemperatureService {

    public float measure() {
        try {
            String baseDir = "/sys/bus/w1/devices";
            int temperature = 0;

            File file = new File(baseDir);

            if (file.exists() == false) {
                return -1000;
            }

            File[] files = file.listFiles();
            log.info("{}", files);

            if (files == null) {
                return -1000;
            }

            List<String> temperatureDirAbsolutePathNames = Arrays.stream(files)
                        .filter(f -> f.isDirectory() && f.getName().startsWith("28"))
                        .map(File::getAbsolutePath)
                        .toList();
            log.info("temperatureDirNames 사이즈 : {}", temperatureDirAbsolutePathNames.size());

            for (String name : temperatureDirAbsolutePathNames) {
                log.info("{}", name);
                String[] cmd = {"/bin/sh", "-c", "cat " + name + "/temperature"};
                Process process = Runtime.getRuntime().exec(cmd);
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line = null;
                log.info("while 문 시작");
                while ((line = br.readLine()) != null) {
                    log.info("내용 : {}", line);
                    temperature += Integer.parseInt(line);
                }
                log.info("while 문 종료");

                process.waitFor();
                process.destroy();
            }

            return (float) temperature / temperatureDirAbsolutePathNames.size() / 1000;
        } catch (IOException e) {
            return -1000;
        } catch (InterruptedException e) {
            return -1001;
        }
    }
}
