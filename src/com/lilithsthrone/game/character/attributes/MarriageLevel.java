package com.lilithsthrone.game.character.attributes;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.utils.UtilText;
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

		private static StringBuilder sb = new StringBuilder();
		public static String getDescription(GameCharacter character, GameCharacter target, AffectionLevel affectionLevel, boolean withColour) {
			sb.setLength(0);
			
			switch(affectionLevel) {
				case NEGATIVE_FIVE_LOATHE:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>[npc.verb(loathe)]</span> [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.verb(loathe)] [npc2.name]."));
					}
					break;
				case NEGATIVE_FOUR_HATE:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>[npc.verb(hate)]</span> [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.verb(hate)] [npc2.name]."));
					}
					break;
				case NEGATIVE_THREE_STRONG_DISLIKE:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>strongly [npc.verb(dislike)]</span> [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] strongly [npc.verb(dislike)] [npc2.name]."));
					}
					break;
				case NEGATIVE_TWO_DISLIKE:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>[npc.verb(dislike)]</span> [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.verb(dislike)] [npc2.name]."));
					}
					break;
				case NEGATIVE_ONE_ANNOYED:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.is] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>annoyed</span> with [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.is] annoyed with [npc2.name]."));
					}
					break;
				case ZERO_NEUTRAL:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.is] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>indifferent</span> towards [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.is] indifferent towards [npc2.name]."));
					}
					break;
				case POSITIVE_ONE_FRIENDLY:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.is] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>friendly</span> towards [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.is] friendly towards [npc2.name]."));
					}
					break;
				case POSITIVE_TWO_LIKE:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>[npc.verb(like)]</span> [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.verb(like)] [npc2.name]."));
					}
					break;
				case POSITIVE_THREE_CARING:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>[npc.verb(care)] about</span> [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.verb(care)] about [npc2.name]."));
					}
					break;
				case POSITIVE_FOUR_LOVE:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>[npc.verb(love)]</span> [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.verb(love)] [npc2.name]."));
					}
					break;
				case POSITIVE_FIVE_WORSHIP:
					if(withColour) {
						sb.append(UtilText.parse(character, target, "[npc.Name] <span style='color:"+affectionLevel.getColour().toWebHexString()+";'>[npc.verb(adore)]</span> [npc2.name]."));
					} else {
						sb.append(UtilText.parse(character, target, "[npc.Name] [npc.verb(adore)] [npc2.name]."));
					}
					break;
			}
			
			return sb.toString();
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
	
	public enum MaritalStatus {
	
		DATING,
	
	    BROKEN_UP,
		
		MARRIED,
		
		DIVORCED,
		
		CRUSH,
		
		NONE;
		
	}
}
