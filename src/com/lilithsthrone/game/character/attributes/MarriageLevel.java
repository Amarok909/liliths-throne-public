package com.lilithsthrone.game.character.attributes;

import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

/**
 * @since 0.1.95
 * @version 0.1.95
 * @author Amarok
 */
public class MarriageLevel {
	public enum BondLevel {
		
		/** -100 to -30*/
		DIVORCING("divorcing", -100, -30, PresetColour.AFFECTION_NEGATIVE_TWO),
	
		/** -30 to 30*/
		DATING("neutral", -30, 30, PresetColour.AFFECTION_POSITIVE_ONE),
	
		/** -30 to 100*/
		SOULMATES("soul mates", 30, 100, PresetColour.AFFECTION_POSITIVE_FIVE);
		
		
		private String name;
		private int minimumValue, maximumValue;
		private Colour colour;
	
		private BondLevel(String name, int minimumValue, int maximumValue, Colour colour) {
			this.name = name;
			this.minimumValue = minimumValue;
			this.maximumValue = maximumValue;
			this.colour = colour;
		}
		
		public String getName() {
			return name;
		}
	
		public int getMinimumValue() {
			return minimumValue;
		}
	
		public int getMaximumValue() {
			return maximumValue;
		}
		
		public int getMedianValue() {
			return (minimumValue + maximumValue) / 2;
		}
	
		public Colour getColour() {
			return colour;
		}
	
		public static BondLevel getBondLevelFromValue(float value){
			for(BondLevel al : BondLevel.values()) {
				if(value>=al.getMinimumValue() && value<al.getMaximumValue())
					return al;
			}
			return SOULMATES;
		}
	}
	
	public enum RelationshipStatus {
	
		DATING,
	
	    BROKEN_UP,
		
		MARRIED,
		
		DIVORCED,
		
		CRUSH;
		
	}
}
