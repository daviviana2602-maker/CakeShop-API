package br.com.davi.spring_boot_first.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ResendConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();      // create an HTTP client (the object used to take a requisition to the Resend)
    }

}