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
    public static final UUID BEER_1_UUID = UUID.fromString("d36b163a-6972-432c-b3b5-d73d73c38141");
    public static final UUID BEER_2_UUID = UUID.fromString("0a0c11d8-e1e9-40c2-b47a-239cf763d7ae");
    public static final UUID BEER_3_UUID = UUID.fromString("e88bb4f6-3807-42e0-ad81-7c053b78f55e");

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
