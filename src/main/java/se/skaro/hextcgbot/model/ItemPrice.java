package se.skaro.hextcgbot.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CARD database table.
 * 
 */
@Entity
@Table (name="ITEMPRICE")
public final class ItemPrice extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String weightedAveragePlatinum; 
	private String numberOfAuctionsPlatinum;
	private String averagePlatinum;
	private String minPricePlatinum;
	private String maxPricePlatinum;
	private String weightedAverageGold;
	private String numberOfAuctionsGold;
	private String averageGold;
	private String minPriceGold;
	private String maxPriceGold;

	
	public String getListedPrice(float platRatio) {

		if (Float.valueOf(weightedAveragePlatinum) == 0) {
			return "[" + name + ": " + weightedAverageGold + "g] ";

		} else if (Float.valueOf(weightedAverageGold) == 0) {
			return "[" + name + ": " + weightedAveragePlatinum + "p] ";
		}

		if (Float.valueOf(weightedAveragePlatinum) * platRatio >= Float.valueOf(weightedAverageGold)) {
			return "[" + name + ": " + weightedAverageGold + "g, " + weightedAveragePlatinum + "p] ";
		}

		return "[" + name + ": " + weightedAveragePlatinum + "p, " + weightedAverageGold + "g] ";
	}


	public ItemPrice() {
	}

	public String getWeightedAveragePlatinum() {
		return weightedAveragePlatinum;
	}

	public String getNumberOfAuctionsPlatinum() {
		return numberOfAuctionsPlatinum;
	}

	public String getAveragePlatinum() {
		return averagePlatinum;
	}

	public String getMinPricePlatinum() {
		return minPricePlatinum;
	}

	public String getMaxPricePlatinum() {
		return maxPricePlatinum;
	}

	public String getWeightedAverageGold() {
		return weightedAverageGold;
	}

	public String getNumberOfAuctionsGold() {
		return numberOfAuctionsGold;
	}

	public String getAverageGold() {
		return averageGold;
	}

	public String getMinPriceGold() {
		return minPriceGold;
	}

	public String getMaxPriceGold() {
		return maxPriceGold;
	}

	@Override
	public String toString() {
		return "Price [name=" + name + ", weightedAveragePlatinum=" + weightedAveragePlatinum
				+ ", numberOfAuctionsPlatinum=" + numberOfAuctionsPlatinum + ", averagePlatinum=" + averagePlatinum
				+ ", minPricePlatinum=" + minPricePlatinum + ", maxPricePlatinum=" + maxPricePlatinum
				+ ", weightedAverageGold=" + weightedAverageGold + ", numberOfAuctionsGold=" + numberOfAuctionsGold
				+ ", averageGold=" + averageGold + ", minPriceGold=" + minPriceGold + ", maxPriceGold=" + maxPriceGold
				+ "]";
	}

}