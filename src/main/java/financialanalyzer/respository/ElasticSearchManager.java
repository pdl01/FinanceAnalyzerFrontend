
package financialanalyzer.respository;

import financialanalyzer.config.ElasticSearchConfiguration;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pldor
 */
public abstract class ElasticSearchManager {
    
    private static final Logger logger = Logger.getLogger(ElasticSearchManager.class.getName());

    @Autowired
    protected ElasticSearchConfiguration elasticSearchConfiguration;

    protected RestHighLevelClient buildClient() {
        RestHighLevelClient client = null;
        try {
            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(this.elasticSearchConfiguration.getElasticsearch_primary_host(), Integer.parseInt(this.elasticSearchConfiguration.getElasticsearch_primary_port()), this.elasticSearchConfiguration.getElasticsearch_primary_protocol()), new HttpHost(this.elasticSearchConfiguration.getElasticsearch_secondary_host(), Integer.parseInt(this.elasticSearchConfiguration.getElasticsearch_secondary_port()), this.elasticSearchConfiguration.getElasticsearch_secondary_protocol())));
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        return client;
    }
    
    protected void closeClient(RestHighLevelClient client) {
        if (client != null) {
            try {
                client.close();
            } catch (IOException ex) {
                logger.severe(ex.getMessage());
            }
        }
    }    
    
    public boolean isServiceAvailable() {
        RestHighLevelClient client = this.buildClient();
        boolean clientStatus = true;
        if (client == null) {    
            clientStatus = false;
        } 
        this.closeClient(client);
        return clientStatus;
    }
}
