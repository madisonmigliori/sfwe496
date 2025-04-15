package com.sfwe496.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.sfwe496.user.config.TestSecurityConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestSecurityConfig.class)
class UserPreferencesApplicationTests {

	@Test
	void contextLoads() {
	}

}
