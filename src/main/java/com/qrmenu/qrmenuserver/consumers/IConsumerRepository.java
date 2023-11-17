package com.qrmenu.qrmenuserver.consumers;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsumerRepository extends JpaRepository<ConsumerModel, UUID> {

    ConsumerModel findByName(String name);
}
