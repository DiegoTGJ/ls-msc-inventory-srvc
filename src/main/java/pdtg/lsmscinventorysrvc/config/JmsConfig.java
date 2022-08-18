package pdtg.lsmscinventorysrvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Created by Diego T. 07-08-2022
 */
@Configuration
public class JmsConfig {

    public static final String NEW_INVENTORY_QUEUE= "new-inventory";
    public static final String ALLOCATE_ORDER_QUEUE= "allocate-order";
    public static final String ALLOCATE_ORDER_RESULT_QUEUE= "allocate-order-result";

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
