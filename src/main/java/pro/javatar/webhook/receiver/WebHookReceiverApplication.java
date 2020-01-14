/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pro.javatar.webhook.receiver.config.WebHookConfig;
import pro.javatar.webhook.receiver.config.WebHookProdConfig;

@Configuration
@EnableConfigurationProperties({WebHookProdConfig.class, WebHookConfig.class})
@SpringBootApplication(scanBasePackages = "pro.javatar")
public class WebHookReceiverApplication {

	public static void main(String[] args) {
        SpringApplication.run(WebHookReceiverApplication.class, args); //NOSONAR
    }

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
