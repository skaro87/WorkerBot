package se.skaro.hextcgbot.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CARD database table.
 * 
 */
@Entity
public final class ItemPrice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;
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

	public ItemPrice(String name, String line, String line2, String line3, String line4, String line5, String line6,
			String line7, String line8, String line9, String line10) {
		super();
		this.name = name;
		this.weightedAveragePlatinum = line;
		this.numberOfAuctionsPlatinum = line2;
		this.averagePlatinum = line3;
		this.minPricePlatinum = line4;
		this.maxPricePlatinum = line5;
		this.weightedAverageGold = line6;
		this.numberOfAuctionsGold = line7;
		this.averageGold = line8;
		this.minPriceGold = line9;
		this.maxPriceGold = line10;
	}
	
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

	public String getName() {
		return name;
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