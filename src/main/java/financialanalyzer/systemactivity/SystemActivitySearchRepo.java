/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.systemactivity;

import financialanalyzer.elasticsearch.ElasticSearchManager;
import financialanalyzer.objects.StockHistory;
import financialanalyzer.respository.StockHistorySearchRepo;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
public class SystemActivitySearchRepo extends ElasticSearchManager implements SystemActivityRepo {

    private static final Logger logger = Logger.getLogger(SystemActivitySearchRepo.class.getName());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public SystemActivity submit(SystemActivity _item) {
        if (_item == null) {
            return null;
        }
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return null;
        }

        IndexRequest indexRequest = new IndexRequest("systemactivities", "systemactivity", _item.getId())
                .source("id", _item.getId(),
                        "recordDate", _item.getRecordDate(),
                        "symbol", _item.getSymbol(),
                        "exchange", _item.getExchange(),
                        "activityType", _item.getActivityType(),
                        "activityMessage", _item.getActivityMessage()
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
        return _item;
    }

    @Override
    public boolean delete(SystemActivity _item) {
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return false;
        }

        DeleteRequest request = new DeleteRequest("systemactivities", "systemactivity", _item.getId());

        try {
            RequestOptions options = null;
            client.delete(request, options);
            //CreateIndexRequest request = new CreateIndexRequest("users");
            //client.
            //CreateIndexResponse  createIndexResponse = client.indices().indices().create(request);
        } catch (IOException ex) {
            logger.severe("Error when deleting stock history " + ex.getMessage());

        } catch (Exception ex) {
            logger.severe("Error when deleting stock history " + ex.getMessage());

        }

        this.closeClient(client);
        return true;
    }

    @Override
    public List<SystemActivity> searchForSystemActivity(SystemActivitySearchProperties _sp) {
        List<SystemActivity> shs = new ArrayList<>();
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return shs;
        }

        SearchRequest searchRequest = new SearchRequest("systemactivities");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        QueryBuilder matchQueryBuilder = null;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (_sp.getStockExchange() != null) {
            boolQuery.must(QueryBuilders.matchQuery("exchange", _sp.getStockExchange()));

        }
        if (_sp.getStockSymbol() != null) {
            boolQuery.must(QueryBuilders.matchQuery("symbol", _sp.getStockSymbol()));

        }
        if (_sp.getActivityType() != null) {
            try {
                boolQuery.must(QueryBuilders.matchQuery("activityType", sdf.parse(_sp.getActivityType())));
            } catch (ParseException ex) {
                logger.log(Level.SEVERE, null, ex);
            }

        }

        //.fuzziness(Fuzziness.AUTO);
        searchSourceBuilder.query(boolQuery).from(_sp.getStartResults()).size(_sp.getNumResults());

        if (_sp.getSortField() != null) {
            //TODO sort based on dimension type
            if ("ASC".equalsIgnoreCase(_sp.getSortOrder())) {
                searchSourceBuilder.sort(_sp.getSortField(), SortOrder.ASC);
            } else {
                searchSourceBuilder.sort(_sp.getSortField(), SortOrder.DESC);
            }
        }

        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();

            for (SearchHit hit : searchHits) {
                //build some artificial items that will house basic info about the artifact, without hitting the main db again. (id,title)
                if (hit.getType().equalsIgnoreCase("systemactivity")) {
                    String sourceAsString = hit.getSourceAsString();
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    SystemActivity sh = this.buildSystemActivityFromSourceMap(sourceAsMap);

                    shs.add(sh);
                }
                // do something with the SearchHit
            }
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }

        this.closeClient(client);

        return shs;

    }

    private SystemActivity buildSystemActivityFromSourceMap(Map<String, Object> _sourceAsMap) {
        SystemActivity sa = new SystemActivity();
        String id = (String) _sourceAsMap.get("id");

        String recordDate = ((String) _sourceAsMap.get("recordDate")).substring(0, 10);

        String symbol = (String) _sourceAsMap.get("symbol");
        String exchange = (String) _sourceAsMap.get("exchange");
        String activityType = (String) _sourceAsMap.get("activityType");

        String activityMessage = (String) _sourceAsMap.get("activityMessage");
        
        sa.setId(id);
        sa.setSymbol(symbol);
        sa.setActivityMessage(activityMessage);
        sa.setActivityType(activityType);
        sa.setExchange(exchange);
        sa.setRecordDateAsString(recordDate);
        try {
            sa.setRecordDate(sdf.parse(recordDate));    
        } catch (Exception e) {
            logger.severe("Cannot convert recordDate from search to java date");
        }

        return sa;
    }

}
