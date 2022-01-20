package com.example.springrabbitmqproducesimpl;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String QUEUE_EXCEL = "xlsx_queue";
    public static final String EXCHANGE_EXCEL = "xlsx_exchange";
    public static final String ROUTING_KEY_EXCEL = "xlsx_routingKey";

    @Bean
    public Queue queue_excel() {
        return new Queue(QUEUE_EXCEL);
    }

    @Bean
    public TopicExchange exchange_excel() {
        return new TopicExchange(EXCHANGE_EXCEL);
    }

    @Bean
    public Binding bindingExcel(Queue queueExcel, TopicExchange exchangeExcel) {
        return BindingBuilder
                .bind(queueExcel)
                .to(exchangeExcel)
                .with(ROUTING_KEY_EXCEL);
    }

    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }



}
