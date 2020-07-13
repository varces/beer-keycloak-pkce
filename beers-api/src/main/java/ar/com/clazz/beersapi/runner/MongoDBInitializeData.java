/**
 * 
 */
package ar.com.clazz.beersapi.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ar.com.clazz.beersapi.model.Beer;
import ar.com.clazz.beersapi.repository.BeerRepository;

/**
 * @author Cesar Vargas
 *
 */
@Component
public class MongoDBInitializeData implements ApplicationRunner {

	private BeerRepository beerRepository;

	@Autowired
    public MongoDBInitializeData(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.beerRepository.deleteAll();
		
		Beer beer = new Beer();
		beer.setId("1");
		beer.setName("Electric India");
		beer.setTagline("Vibrant Hoppy Saison.");
		beer.setDescription("Re-brewed as a spring seasonal, this beer – which appeared originally as an Equity Punk shareholder creation – retains its trademark spicy, fruity edge. A perfect blend of Belgian Saison and US IPA.");
		beer.setImage_url("https://images.punkapi.com/v2/6.png");
		
		this.beerRepository.save(beer);
		
		beer = new Beer();
		beer.setId("2");
		beer.setName("Trashy Blonde");
		beer.setTagline("You Know You Shouldn't");
		beer.setDescription("A titillating, neurotic, peroxide punk of a Pale Ale");
		beer.setImage_url("https://images.punkapi.com/v2/2.png");
		
		this.beerRepository.save(beer);
		
		beer = new Beer();
		beer.setId("3");
		beer.setName("Avery Brown Dredge");
		beer.setTagline("Bloggers' Imperial Pilsner.");
		beer.setDescription("An Imperial Pilsner in collaboration with beer writers. Tradition. Homage. Revolution. We wanted to showcase the awesome backbone of the Czech brewing tradition.");
		beer.setImage_url("https://images.punkapi.com/v2/5.png");
		
		this.beerRepository.save(beer);
		
		
		
	}

}
