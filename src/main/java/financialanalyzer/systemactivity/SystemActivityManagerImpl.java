/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialanalyzer.systemactivity;

import financialanalyzer.config.ActiveMQConfig;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author pldor
 */
@Component
public class SystemActivityManagerImpl implements SystemActivityManager {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void saveSystemActivity(String _symbol, String _exchange, String _type, String _message) {
        SystemActivity sa = new SystemActivity();
        sa.setActivityMessage(_message);
        sa.setActivityType(_type);
        sa.setExchange(_exchange);
        sa.setSymbol(_symbol);
        sa.setRecordDate(new Date());
        sa.setId(UUID.randomUUID().toString());

        this.jmsTemplate.convertAndSend(ActiveMQConfig.SYSTEM_ACTIVITY_QUEUE, sa);

    }
}
