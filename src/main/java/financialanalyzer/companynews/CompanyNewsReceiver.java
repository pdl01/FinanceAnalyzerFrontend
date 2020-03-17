/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.companynews;

import financialanalyzer.config.ActiveMQConfig;
import financialanalyzer.download.StockHistoryDownloadTask;
import financialanalyzer.download.StockHistoryDownloadTaskReceiver;
import java.util.logging.Logger;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class CompanyNewsReceiver {

    private static final Logger LOGGER = Logger.getLogger(CompanyNewsReceiver.class.getName());

    @Autowired
    private CompanyNewsRepo companyNewsSearchRepo;
    
    @JmsListener(destination = ActiveMQConfig.COMPANY_NEWS_QUEUE)
    public void receiveMessage(@Payload CompanyNewsItem _companyNewsItem,
            @Headers MessageHeaders headers,
            Message message, Session session) {
        LOGGER.info("Received " + _companyNewsItem.getId());
        this.companyNewsSearchRepo.submit(_companyNewsItem);
    }
}
