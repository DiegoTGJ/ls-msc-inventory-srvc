package pdtg.lsmscinventorysrvc.bootstrap;

import pdtg.lsmscinventorysrvc.domain.BeerInventory;
import pdtg.lsmscinventorysrvc.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Component
public class BeerInventoryBootstrap implements CommandLineRunner {
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    public static final UUID BEER_1_UUID = UUID.fromString("6cedc77b-9372-4bb7-b979-5c13281e56b8");
    public static final UUID BEER_2_UUID = UUID.fromString("75257f45-a5be-42b0-9849-550b7c866af9");
    public static final UUID BEER_3_UUID = UUID.fromString("9cae33ab-af86-4a69-a5ff-0bc2ab50734b");

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public void run(String... args)   {
        if(beerInventoryRepository.count() == 0){
            loadInitialInv();
        }
    }

    private void loadInitialInv() {
        beerInventoryRepository.save(BeerInventory
                .builder()
                .beerId(BEER_1_UUID)
                .upc(BEER_1_UPC)
                .quantityOnHand(50)
                .build());

        beerInventoryRepository.save(BeerInventory
                .builder()
                .beerId(BEER_2_UUID)
                .upc(BEER_2_UPC)
                .quantityOnHand(50)
                .build());

        beerInventoryRepository.saveAndFlush(BeerInventory
                .builder()
                .beerId(BEER_3_UUID)
                .upc(BEER_3_UPC)
                .quantityOnHand(50)
                .build());

        log.debug("Loaded Inventory. Record count: " + beerInventoryRepository.count());
    }
}
