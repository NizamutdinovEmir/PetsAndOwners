package nizamutdinov..config;

import nizamutdinov..OwnerDTO;
import nizamutdinov..OwnerDeleteRequest;
import nizamutdinov..OwnerGetRequest;
import nizamutdinov..OwnerUpdateRequest;
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
    public ConsumerFactory<String, OwnerDTO> consumerFactory() {
        JsonDeserializer<OwnerDTO> deserializer = new JsonDeserializer<>(OwnerDTO.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "owner-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OwnerDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OwnerDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OwnerGetRequest> ownerGetRequestConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,            "owner-service-get");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,   "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,    StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,  JsonDeserializer.class);

        JsonDeserializer<OwnerGetRequest> deser =
                new JsonDeserializer<>(OwnerGetRequest.class);
        deser.addTrustedPackages("*");
        deser.setRemoveTypeHeaders(false);      // <— важно
        deser.setUseTypeMapperForKey(false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deser
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OwnerGetRequest>
    ownerGetRequestKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OwnerGetRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ownerGetRequestConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OwnerDeleteRequest> ownerDeleteRequestConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,            "owner-service-delete");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,   "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,   StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<OwnerDeleteRequest> deser =
                new JsonDeserializer<>(OwnerDeleteRequest.class);
        deser.addTrustedPackages("*");
        deser.setRemoveTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deser
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OwnerDeleteRequest>
    ownerDeleteRequestKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OwnerDeleteRequest>();
        factory.setConsumerFactory(ownerDeleteRequestConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OwnerUpdateRequest> ownerUpdateRequestConsumerFactory() {
        Map<String,Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG,          "owner-service-update",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        );
        JsonDeserializer<OwnerUpdateRequest> deser =
                new JsonDeserializer<>(OwnerUpdateRequest.class);
        deser.addTrustedPackages("*");
        deser.setRemoveTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), deser
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OwnerUpdateRequest>
    ownerUpdateRequestKafkaListenerContainerFactory() {
        var f = new ConcurrentKafkaListenerContainerFactory<String, OwnerUpdateRequest>();
        f.setConsumerFactory(ownerUpdateRequestConsumerFactory());
        return f;
    }

}