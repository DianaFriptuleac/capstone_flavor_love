package dianafriptuleac.capstone_flavor_love.config;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServerConfig {

    @Bean
    public Cloudinary cloudinary(@Value("${cloudinary.name}") String cloudName,
                                 @Value("${cloudinary.key}") String apiKey,
                                 @Value("${cloudinary.secret}") String apiSecret) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }

    //bean per API esternna
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
