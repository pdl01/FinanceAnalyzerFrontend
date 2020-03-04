/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author pldor
 */
@Configuration
@PropertySource(value = "file:${app_home}/config/application.properties")
public class ElasticSearchConfiguration {
    @Value("${elasticsearch_primary_host}")
    private String elasticsearch_primary_host;

    @Value("${elasticsearch_primary_port}")
    private String elasticsearch_primary_port;

    @Value("${elasticsearch_primary_protocol}")
    private String elasticsearch_primary_protocol;

    @Value("${elasticsearch_secondary_host}")
    private String elasticsearch_secondary_host;

    @Value("${elasticsearch_secondary_port}")
    private String elasticsearch_secondary_port;

    @Value("${elasticsearch_secondary_protocol}")
    private String elasticsearch_secondary_protocol;

    public String getElasticsearch_primary_host() {
        return elasticsearch_primary_host;
    }

    public void setElasticsearch_primary_host(String elasticsearch_primary_host) {
        this.elasticsearch_primary_host = elasticsearch_primary_host;
    }

    public String getElasticsearch_primary_port() {
        return elasticsearch_primary_port;
    }

    public void setElasticsearch_primary_port(String elasticsearch_primary_port) {
        this.elasticsearch_primary_port = elasticsearch_primary_port;
    }

    public String getElasticsearch_primary_protocol() {
        return elasticsearch_primary_protocol;
    }

    public void setElasticsearch_primary_protocol(String elasticsearch_primary_protocol) {
        this.elasticsearch_primary_protocol = elasticsearch_primary_protocol;
    }

    public String getElasticsearch_secondary_host() {
        return elasticsearch_secondary_host;
    }

    public void setElasticsearch_secondary_host(String elasticsearch_secondary_host) {
        this.elasticsearch_secondary_host = elasticsearch_secondary_host;
    }

    public String getElasticsearch_secondary_port() {
        return elasticsearch_secondary_port;
    }

    public void setElasticsearch_secondary_port(String elasticsearch_secondary_port) {
        this.elasticsearch_secondary_port = elasticsearch_secondary_port;
    }

    public String getElasticsearch_secondary_protocol() {
        return elasticsearch_secondary_protocol;
    }

    public void setElasticsearch_secondary_protocol(String elasticsearch_secondary_protocol) {
        this.elasticsearch_secondary_protocol = elasticsearch_secondary_protocol;
    }
    
    
}
