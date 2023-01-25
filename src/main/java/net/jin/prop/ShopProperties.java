package net.jin.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("org.hdcd")
public class ShopProperties {

	private String uploadPath;

	private String secretKey;

}
