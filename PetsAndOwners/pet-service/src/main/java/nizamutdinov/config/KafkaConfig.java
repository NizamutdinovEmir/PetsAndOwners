package nizamutdinov..config;

import nizamutdinov..PetDTO;
import nizamutdinov..PetDeleteRequest;
import nizamutdinov..PetGetRequest;
import nizamutdinov..PetUpdateRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, PetDTO> consumerFactory() {
        JsonDeserializer<PetDTO> deserializer = new JsonDeserializer<>(PetDTO.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "pet-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PetDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PetDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PetGetRequest> petGetRequestConsumerFactory() {
        JsonDeserializer<PetGetRequest> deserializer =
                new JsonDeserializer<>(PetGetRequest.class);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false);

        Map<String, Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG,            "pet-service-get",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,   "earliest"
        );

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PetGetRequest>
    petGetRequestKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PetGetRequest>();
        factory.setConsumerFactory(petGetRequestConsumerFactory());
        return factory;
    }

    // 2) DELETE
    @Bean
    public ConsumerFactory<String, PetDeleteRequest> petDeleteRequestConsumerFactory() {
        JsonDeserializer<PetDeleteRequest> deserializer =
                new JsonDeserializer<>(PetDeleteRequest.class);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false);

        Map<String, Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG,            "pet-service-delete",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,   "earliest"
        );

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PetDeleteRequest>
    petDeleteRequestKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PetDeleteRequest>();
        factory.setConsumerFactory(petDeleteRequestConsumerFactory());
        return factory;
    }

    // 3) UPDATE
    @Bean
    public ConsumerFactory<String, PetUpdateRequest> petUpdateRequestConsumerFactory() {
        JsonDeserializer<PetUpdateRequest> deserializer =
                new JsonDeserializer<>(PetUpdateRequest.class);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false);

        Map<String, Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG,            "pet-service-update",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,   "earliest"
        );

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PetUpdateRequest>
    petUpdateRequestKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PetUpdateRequest>();
        factory.setConsumerFactory(petUpdateRequestConsumerFactory());
        return factory;
    }
}
