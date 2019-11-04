package com.ppi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan({"com.ppi.claimHartaRepUpload"})
public class PpiApplication {
	private int repeatCount = 1;
	public static void main(String[] args) {
		
		SpringApplication.run(PpiApplication.class, args);
		
				
		//new SpringApplicationBuilder(PpiApplication.class)
		//.properties("spring.config.location:file:/home/ec2-user/PPIPH1/ppi/application.properties").build().run(args);

	/*public static void main(String[] args) {
		new SpringApplicationBuilder(PpiApplication.class)
	.properties("spring.config.location:file:/home/ec2-user/ppi/config/application.properties").build().run(args);
	}
	*/
	
	/*public static void main(String[] args) {
	new SpringApplicationBuilder(PpiApplication.class)
	.properties("spring.config.location:file:/home/ec2-user/PPIPH1/ppi/application.properties").build().run(args);
	}
	*/
	
	}

}


