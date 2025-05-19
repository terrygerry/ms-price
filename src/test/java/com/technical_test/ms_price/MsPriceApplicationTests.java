package com.technical_test.ms_price;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MsPriceApplicationTests {

	@Autowired
	private MsPriceApplication applicationContext;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(applicationContext);
	}

}
