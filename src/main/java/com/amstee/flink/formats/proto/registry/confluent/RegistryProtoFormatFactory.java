package com.amstee.flink.formats.proto.registry.confluent;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.format.EncodingFormat;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.DeserializationFormatFactory;
import org.apache.flink.table.factories.DynamicTableFactory;
import org.apache.flink.table.factories.FactoryUtil;
import org.apache.flink.table.factories.SerializationFormatFactory;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RegistryProtoFormatFactory implements DeserializationFormatFactory, SerializationFormatFactory {
    public static final String IDENTIFIER = "proto-confluent";

    @Override
    public DecodingFormat<DeserializationSchema<RowData>> createDecodingFormat(DynamicTableFactory.Context context, ReadableConfig formatOptions) {
        FactoryUtil.validateFactoryOptions(this, formatOptions);
        String schemaRegistryURL = formatOptions.get(ProtoConfluentFormatOptions.URL);
        String topic = formatOptions.get(ProtoConfluentFormatOptions.TOPIC);
        Boolean is_key = formatOptions.get(ProtoConfluentFormatOptions.IS_KEY);
        Map<String, String> optionalPropertiesMap = buildOptionalPropertiesMap(formatOptions);
        if (optionalPropertiesMap == null) {
            optionalPropertiesMap = new HashMap<>();
        }
        optionalPropertiesMap.put("schema.registry.url", schemaRegistryURL);

        return new ProtoDecodingFormat(schemaRegistryURL, topic, is_key, optionalPropertiesMap);
    }

    @Override
    public EncodingFormat<SerializationSchema<RowData>> createEncodingFormat(DynamicTableFactory.Context context, ReadableConfig formatOptions) {
        FactoryUtil.validateFactoryOptions(this, formatOptions);
        String schemaRegistryURL = formatOptions.get(ProtoConfluentFormatOptions.URL);
        String topic = formatOptions.get(ProtoConfluentFormatOptions.TOPIC);
        Boolean is_key = formatOptions.get(ProtoConfluentFormatOptions.IS_KEY);
        Map<String, String> optionalPropertiesMap = buildOptionalPropertiesMap(formatOptions);

        return new ProtoEncodingFormat(schemaRegistryURL, topic, is_key, optionalPropertiesMap);
    }

    @Override
    public String factoryIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Set<ConfigOption<?>> forwardOptions() {
        return Stream.of(
                        ProtoConfluentFormatOptions.URL,
                        ProtoConfluentFormatOptions.TOPIC,
                        ProtoConfluentFormatOptions.IS_KEY,
                        ProtoConfluentFormatOptions.PROPERTIES,
                        ProtoConfluentFormatOptions.SSL_KEYSTORE_LOCATION,
                        ProtoConfluentFormatOptions.SSL_KEYSTORE_PASSWORD,
                        ProtoConfluentFormatOptions.SSL_TRUSTSTORE_LOCATION,
                        ProtoConfluentFormatOptions.SSL_TRUSTSTORE_PASSWORD,
                        ProtoConfluentFormatOptions.BASIC_AUTH_CREDENTIALS_SOURCE,
                        ProtoConfluentFormatOptions.BASIC_AUTH_USER_INFO,
                        ProtoConfluentFormatOptions.BEARER_AUTH_CREDENTIALS_SOURCE,
                        ProtoConfluentFormatOptions.BEARER_AUTH_TOKEN)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        Set<ConfigOption<?>> options = new HashSet<>();
        options.add(ProtoConfluentFormatOptions.URL);
        options.add(ProtoConfluentFormatOptions.TOPIC);
        return options;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        Set<ConfigOption<?>> options = new HashSet<>();
        options.add(ProtoConfluentFormatOptions.IS_KEY);
        options.add(ProtoConfluentFormatOptions.PROPERTIES);
        options.add(ProtoConfluentFormatOptions.SSL_KEYSTORE_LOCATION);
        options.add(ProtoConfluentFormatOptions.SSL_KEYSTORE_PASSWORD);
        options.add(ProtoConfluentFormatOptions.SSL_TRUSTSTORE_LOCATION);
        options.add(ProtoConfluentFormatOptions.SSL_TRUSTSTORE_PASSWORD);
        options.add(ProtoConfluentFormatOptions.BASIC_AUTH_CREDENTIALS_SOURCE);
        options.add(ProtoConfluentFormatOptions.BASIC_AUTH_USER_INFO);
        options.add(ProtoConfluentFormatOptions.BEARER_AUTH_CREDENTIALS_SOURCE);
        options.add(ProtoConfluentFormatOptions.BEARER_AUTH_TOKEN);
        return options;
    }

    public static @Nullable Map<String, String> buildOptionalPropertiesMap(
            ReadableConfig formatOptions) {
        final Map<String, String> properties = new HashMap<>();

        formatOptions.getOptional(ProtoConfluentFormatOptions.PROPERTIES).ifPresent(properties::putAll);

        formatOptions
                .getOptional(ProtoConfluentFormatOptions.SSL_KEYSTORE_LOCATION)
                .ifPresent(v -> properties.put("schema.registry.ssl.keystore.location", v));
        formatOptions
                .getOptional(ProtoConfluentFormatOptions.SSL_KEYSTORE_PASSWORD)
                .ifPresent(v -> properties.put("schema.registry.ssl.keystore.password", v));
        formatOptions
                .getOptional(ProtoConfluentFormatOptions.SSL_TRUSTSTORE_LOCATION)
                .ifPresent(v -> properties.put("schema.registry.ssl.truststore.location", v));
        formatOptions
                .getOptional(ProtoConfluentFormatOptions.SSL_TRUSTSTORE_PASSWORD)
                .ifPresent(v -> properties.put("schema.registry.ssl.truststore.password", v));
        formatOptions
                .getOptional(ProtoConfluentFormatOptions.BASIC_AUTH_CREDENTIALS_SOURCE)
                .ifPresent(v -> properties.put("basic.auth.credentials.source", v));
        formatOptions
                .getOptional(ProtoConfluentFormatOptions.BASIC_AUTH_USER_INFO)
                .ifPresent(v -> properties.put("basic.auth.user.info", v));
        formatOptions
                .getOptional(ProtoConfluentFormatOptions.BEARER_AUTH_CREDENTIALS_SOURCE)
                .ifPresent(v -> properties.put("bearer.auth.credentials.source", v));
        formatOptions
                .getOptional(ProtoConfluentFormatOptions.BEARER_AUTH_TOKEN)
                .ifPresent(v -> properties.put("bearer.auth.token", v));

        if (properties.isEmpty()) {
            return null;
        }
        return properties;
    }
}
