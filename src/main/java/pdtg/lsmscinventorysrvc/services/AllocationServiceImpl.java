package pdtg.lsmscinventorysrvc.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pdtg.ls.brewery.model.BeerOrderDto;
import pdtg.ls.brewery.model.BeerOrderLineDto;
import pdtg.lsmscinventorysrvc.domain.BeerInventory;
import pdtg.lsmscinventorysrvc.repositories.BeerInventoryRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Diego T. 18-08-2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public boolean allocateOrder(BeerOrderDto beerOrderDto) {
        log.debug("Allocating OrderId: "+beerOrderDto.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(beerOrderLine -> {
            int orderQuantity = beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0;
            int quantityAllocated = beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0;
            int qtyToAllocate = orderQuantity -quantityAllocated;
            if (qtyToAllocate > 0){
                allocateBeerOrderLine(beerOrderLine);
            }
            totalOrdered.getAndAdd(orderQuantity);
            totalAllocated.getAndAdd(quantityAllocated);
        });
        log.debug("Total Ordered "+totalOrdered.get()+" Total Allocated: "+totalAllocated.get());
        return totalOrdered.get() == totalAllocated.get();
    }

    private void allocateBeerOrderLine(BeerOrderLineDto beerOrderLine){
        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(beerOrderLine.getUpc());

        beerInventoryList.forEach(beerInventory -> {
            int inventory = beerInventory.getQuantityOnHand() != null ? beerInventory.getQuantityOnHand() : 0;
            int orderQty = beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0;
            int allocatedQty = beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0;
            int qtyToAllocate = orderQty - allocatedQty;
            if (inventory >= qtyToAllocate){
                inventory = inventory - qtyToAllocate;
                beerOrderLine.setQuantityAllocated(beerOrderLine.getOrderQuantity());
                beerInventory.setQuantityOnHand(inventory);

                beerInventoryRepository.save(beerInventory);
            } else if (inventory > 0) {
                beerOrderLine.setQuantityAllocated(allocatedQty + inventory);
                beerInventory.setQuantityOnHand(0);

                beerInventoryRepository.delete(beerInventory);
            }
        });
    }
}
