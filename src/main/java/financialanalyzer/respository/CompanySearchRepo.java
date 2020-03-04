/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.respository;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.CompanySearchProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class CompanySearchRepo extends ElasticSearchManager implements CompanyRepo {

    private static final Logger logger = Logger.getLogger(CompanySearchRepo.class.getName());

    @Override
    public Company submit(Company _company) {
        if (_company == null) {
            return null;
        }
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return null;
        }

        IndexRequest indexRequest = new IndexRequest("companies", "company", _company.getStockExchange() + "-" + _company.getStockSymbol())
                .source("id", _company.getStockExchange() + "-" + _company.getStockSymbol(), "name", _company.getName(), "symbol", _company.getStockSymbol(),
                        "exchange", _company.getStockExchange(),
                        "sector", _company.getSectors()
                );

        try {
            IndexResponse indexResponse = client.index(indexRequest);
            //logger.info(indexResponse.getIndex());
            //logger.info(indexResponse.getResult().name());
        } catch (IOException ex) {
            //ex.printStackTrace();
            logger.severe(ex.getMessage());
        }
        this.closeClient(client);
        return _company;
    }

    @Override
    public boolean delete(Company _company) {
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return false;
        }
        DeleteRequest request = new DeleteRequest("companies", "company", _company.getStockExchange() + "-" + _company.getStockSymbol());

        try {
            RequestOptions options = null;
            client.delete(request, options);
            //CreateIndexRequest request = new CreateIndexRequest("users");
            //client.
            //CreateIndexResponse  createIndexResponse = client.indices().indices().create(request);
        } catch (IOException ex) {
            logger.severe("Error when deleting company " + ex.getMessage());

        } catch (Exception ex) {
            logger.severe("Error when deleting company " + ex.getMessage());

        }

        this.closeClient(client);
        return true;
    }

    @Override
    public List<Company> searchForCompany(CompanySearchProperties _csp) {
        List<Company> companies = new ArrayList<>();
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return companies;
        }

        SearchRequest searchRequest = new SearchRequest("companies");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        QueryBuilder matchQueryBuilder = null;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        if (_csp.getCompanyName() != null) {
            boolQuery.must(QueryBuilders.matchQuery("name", _csp.getCompanyName()));
        } else if (_csp.getStockExchange() != null) {
            boolQuery.must(QueryBuilders.matchQuery("exchange", _csp.getStockExchange()));

        } else if (_csp.getStockSymbol() != null) {
            boolQuery.must(QueryBuilders.matchQuery("symbol", _csp.getStockSymbol()));

        }

        //.fuzziness(Fuzziness.AUTO);
        searchSourceBuilder.query(boolQuery).from(_csp.getStartResults()).size(_csp.getNumResults());

        /*        if (_searchProperties.getSortField() != null) {
            //TODO sort based on dimension type
            if ("ASC".equalsIgnoreCase(_searchProperties.getSortOrder())) {
                searchSourceBuilder.sort(_searchProperties.getSortField(), SortOrder.ASC);
            } else {
                searchSourceBuilder.sort(_searchProperties.getSortField(), SortOrder.DESC);
            }
        }
         */
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);

            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();

            for (SearchHit hit : searchHits) {
                //build some artificial items that will house basic info about the artifact, without hitting the main db again. (id,title)
                if (hit.getType().equalsIgnoreCase("company")) {
                    String sourceAsString = hit.getSourceAsString();
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    Company company = this.buildCompanyFromSourceMap(sourceAsMap);

                    companies.add(company);
                }
                // do something with the SearchHit
            }
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }

        this.closeClient(client);

        return companies;

    }

    private Company buildCompanyFromSourceMap(Map<String, Object> _sourceAsMap) {
        String id = (String) _sourceAsMap.get("id");

        String name = (String) _sourceAsMap.get("name");
        String symbol = (String) _sourceAsMap.get("symbol");
        String exchange = (String) _sourceAsMap.get("exchange");

        Company company = new Company();
        company.setName(name);
        company.setStockExchange(exchange);
        company.setStockSymbol(symbol);
        return company;
    }

}
