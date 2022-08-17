package pdtg.ls.brewery.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Diego T. 07-08-2022
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 307788760459972843L;
    private BeerDto beerDto;
}
