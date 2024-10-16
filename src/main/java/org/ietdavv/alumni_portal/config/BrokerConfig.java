package org.ietdavv.alumni_portal.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    @Bean
    public DirectExchange getDirectExchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    public Queue getQueue() {
        return new Queue("user-queue", true);
    }

    @Bean
    public Binding getBinding(Queue userQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(userQueue).to(directExchange).with(userQueue.getName());
    }

    @Bean
    public MessageConverter getMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
