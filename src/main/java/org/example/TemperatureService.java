package org.example;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class TemperatureService {
    public String measure() {
        String file = "/docker_workspace/temperature.py";
        ProcessBuilder builder = new ProcessBuilder("python3", file);
        builder.redirectErrorStream(true);

        try {
            Process process = builder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));

            return "온도 : " + br.readLine();
        } catch (IOException e) {
            return "온도 측정 오류";
        }
    }
}