/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.systemactivity;

import financialanalyzer.companynews.CompanyNewsItem;
import financialanalyzer.companynews.CompanyNewsReceiver;
import financialanalyzer.config.ActiveMQConfig;
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
public class SystemActivityReceiver {

    private static final Logger LOGGER = Logger.getLogger(SystemActivityReceiver.class.getName());
    
    @Autowired
    private SystemActivityRepo systemActivitySearchRepo;
    
    @JmsListener(destination = ActiveMQConfig.SYSTEM_ACTIVITY_QUEUE)
    public void receiveMessage(@Payload SystemActivity _systemActivity,
            @Headers MessageHeaders headers,
            Message message, Session session) {
        LOGGER.info("Received " + _systemActivity.getId());
        this.systemActivitySearchRepo.submit(_systemActivity);
    }
}
