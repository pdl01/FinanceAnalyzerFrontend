/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.respository;

import financialanalyzer.objects.Company;
import financialanalyzer.objects.CompanySearchProperties;
import financialanalyzer.objects.StockHistory;
import financialanalyzer.objects.StockHistorySearchProperties;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class StockHistorySearchRepo extends ElasticSearchManager implements StockHistoryRepo {

    private static final Logger logger = Logger.getLogger(StockHistorySearchRepo.class.getName());

    @Override
    public StockHistory submit(StockHistory _item) {
        if (_item == null) {
            return null;
        }
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return null;
        }

        IndexRequest indexRequest = new IndexRequest("stockhistories", "stockhistory", this.getKey(_item))
                .source("id", this.getKey(_item),
                        "recordDate", _item.getRecordDate(),
                        "symbol", _item.getSymbol(),
                        "exchange", _item.getExchange(),
                        "open", _item.getOpen(),
                        "close", _item.getClose(),
                        "percent_gain", _item.getPercent_gain(),
                        "actual_gain", _item.getActual_gain(),
                        "volume", _item.getVolume(),
                        "high",_item.getHigh(),
                        "low",_item.getLow()
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

    private String getKey(StockHistory _item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return _item.getExchange() + "-" + _item.getSymbol() + "-" + sdf.format(_item.getRecordDate());
    }

    @Override
    public boolean delete(StockHistory _item) {
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return false;
        }

        DeleteRequest request = new DeleteRequest("stockhistories", "stockhistory", this.getKey(_item));

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
    public List<StockHistory> searchForStockHistory(StockHistorySearchProperties _shsp) {
        List<StockHistory> shs = new ArrayList<>();
        RestHighLevelClient client = this.buildClient();
        if (client == null) {
            return shs;
        }

        SearchRequest searchRequest = new SearchRequest("stockhistories");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        QueryBuilder matchQueryBuilder = null;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        if (_shsp.getStockExchange() != null) {
            boolQuery.must(QueryBuilders.matchQuery("exchange", _shsp.getStockExchange()));

        } else if (_shsp.getStockSymbol() != null) {
            boolQuery.must(QueryBuilders.matchQuery("symbol", _shsp.getStockSymbol()));

        }

        //.fuzziness(Fuzziness.AUTO);
        searchSourceBuilder.query(boolQuery).from(_shsp.getStartResults()).size(_shsp.getNumResults());

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
                if (hit.getType().equalsIgnoreCase("stockhistory")) {
                    String sourceAsString = hit.getSourceAsString();
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    StockHistory sh = this.buildStockHistoryFromSourceMap(sourceAsMap);

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

    private StockHistory buildStockHistoryFromSourceMap(Map<String, Object> _sourceAsMap) {
        String id = (String) _sourceAsMap.get("id");

        Date recordDate = (Date) _sourceAsMap.get("recordDate");
        String symbol = (String) _sourceAsMap.get("symbol");
        String exchange = (String) _sourceAsMap.get("exchange");
        float openValue = (float) _sourceAsMap.get("open");
        float closeValue = (float) _sourceAsMap.get("close");
        float percent_gain = (float) _sourceAsMap.get("percent_gain");
        float actual_gain = (float) _sourceAsMap.get("actual_gain");
        float high = (float) _sourceAsMap.get("high");
        float low = (float) _sourceAsMap.get("low");
        int volume = (int) _sourceAsMap.get("volume");

        StockHistory sh = new StockHistory();
        sh.setActual_gain(actual_gain);
        sh.setVolume(volume);
        sh.setPercent_gain(percent_gain);
        sh.setOpen(openValue);
        sh.setClose(closeValue);
        sh.setHigh(high);
        sh.setLow(low);
        
        sh.setExchange(exchange);
        sh.setSymbol(symbol);
        sh.setRecordDate(recordDate);
        
        
        return sh;
    }
}
