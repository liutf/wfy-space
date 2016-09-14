package com;

import com.lau.core.exe.GetFreeSpaceSchedule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WfySpaceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WfySpaceApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		GetFreeSpaceSchedule.startProcess();

	}
}
