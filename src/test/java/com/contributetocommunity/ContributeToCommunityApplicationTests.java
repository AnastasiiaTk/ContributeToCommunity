package com.contributetocommunity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@SpringBootTest
@WebAppConfiguration
@Transactional
@TestPropertySource("/test.properties")
public class ContributeToCommunityApplicationTests {

	@Test
	void contextLoads() {
	}

}
