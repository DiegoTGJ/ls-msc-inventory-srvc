package pdtg.lsmscinventorysrvc.services.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pdtg.ls.brewery.model.events.AllocateOrderRequest;
import pdtg.ls.brewery.model.events.AllocateOrderResult;
import pdtg.lsmscinventorysrvc.config.JmsConfig;
import pdtg.lsmscinventorysrvc.services.AllocationService;

/**
 * Created by Diego T. 18-08-2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AllocationRequestListener {

    private final JmsTemplate jmsTemplate;
    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest allocateOrderRequest){
        log.debug("Received allocation request for order id: "+allocateOrderRequest.getBeerOrderDto().getId());
        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
        builder.beerOrderDto(allocateOrderRequest.getBeerOrderDto());
        try{
            boolean isAllocated = allocationService.allocateOrder(allocateOrderRequest.getBeerOrderDto());
            builder.pendingInventory(!isAllocated);
            builder.allocationError(false);
        }catch (Exception e){
            log.error("Allocation failed of Order Id: "+allocateOrderRequest.getBeerOrderDto().getId());
            builder.allocationError(true);
        }
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE,
               builder.build());
    }
}
