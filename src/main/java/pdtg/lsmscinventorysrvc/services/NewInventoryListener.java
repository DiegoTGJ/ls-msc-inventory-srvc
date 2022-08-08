package pdtg.lsmscinventorysrvc.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import pdtg.ls.common.events.NewInventoryEvent;
import pdtg.lsmscinventorysrvc.config.JmsConfig;
import pdtg.lsmscinventorysrvc.domain.BeerInventory;
import pdtg.lsmscinventorysrvc.repositories.BeerInventoryRepository;

/**
 * Created by Diego T. 07-08-2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NewInventoryListener {
    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event){
        log.debug("Got Inventory: "+event.toString());
        beerInventoryRepository.save(BeerInventory.builder()
                        .beerId(event.getBeerDto().getId())
                        .upc(event.getBeerDto().getUpc())
                        .quantityOnHand(event.getBeerDto().getQuantityOnHand())
                .build());
    }
}
