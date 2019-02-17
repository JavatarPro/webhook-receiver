/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pro.javatar.webhook.receiver.config.WebHookConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WebHookReceiverApplicationTest {

	private static final Logger logger = LoggerFactory.getLogger(WebHookReceiverApplicationTest.class);

	@Autowired
	WebHookConfig webHookConfig;

	@Test
	public void contextLoads() {
		logger.info(webHookConfig.toString());
	}

}
