package com.qrmenu.qrmenuserver.consumers;

import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private IConsumerRepository consumerRepository;

    public void registerConsumer(ConsumerModel consumer) {
        consumerRepository.save(consumer);
    }

    public boolean loginConsumer(ConsumerModel consumer) {
        ConsumerModel consumerLogged = consumerRepository.findByName(consumer.getName());

        return consumerLogged.getPassword().equals(consumer.getPassword());
    }
}
