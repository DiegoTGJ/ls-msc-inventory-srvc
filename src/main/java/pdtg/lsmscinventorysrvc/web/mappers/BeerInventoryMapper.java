package pdtg.lsmscinventorysrvc.web.mappers;

import pdtg.lsmscinventorysrvc.domain.BeerInventory;
import pdtg.ls.brewery.model.BeerInventoryDto;
import org.mapstruct.Mapper;


@Mapper(uses = {DateMapper.class})
public interface BeerInventoryMapper {

    BeerInventory beerInventoryDtoToBeerInventory(BeerInventoryDto beerInventoryDTO);

    BeerInventoryDto beerInventoryToBeerInventoryDto(BeerInventory beerInventory);
}
