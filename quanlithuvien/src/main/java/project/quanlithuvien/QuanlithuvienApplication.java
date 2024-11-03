package project.quanlithuvien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.quanlithuvien.ungdung.show.Main;

@SpringBootApplication(scanBasePackages = "project.quanlithuvien")
public class QuanlithuvienApplication {
	public static void main(String[] args) {
		new Thread(() -> {
            SpringApplication.run(QuanlithuvienApplication.class, args);
        }).start();
		Main main = new Main();
    	main.setVisible(true);
	}
}
