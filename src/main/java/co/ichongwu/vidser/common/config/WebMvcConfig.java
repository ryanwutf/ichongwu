package co.ichongwu.vidser.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.TimeZone;

/**
 * Created by whf on 3/21/16.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
   

//    /**
//     * 添加拦截器
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(interceptor).addPathPatterns("/ssp/**");
//    }

    /**
     * 替换为自定义的ObjectMapper
     * @return
     */
    @Bean
    @Primary
    public ObjectMapper nullStringObjectMapper() {
        return new JacksonObjectMapper();
    }

    /**
     * 自定义的Jackson ObjectMapper
     */
    private class JacksonObjectMapper extends ObjectMapper {
        public JacksonObjectMapper() {
            this.setTimeZone(TimeZone.getTimeZone("GMT+8"));

            this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                @Override
                public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                    // 将null替换为""
                    jsonGenerator.writeString("");
                }
            });
        }
    }
}
