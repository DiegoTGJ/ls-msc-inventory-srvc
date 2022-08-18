package pdtg.lsmscinventorysrvc.services;


import pdtg.ls.brewery.model.BeerOrderDto;

/**
 * Created by Diego T. 18-08-2022
 */
public interface AllocationService {
    public boolean allocateOrder(BeerOrderDto beerOrderDto);
}
