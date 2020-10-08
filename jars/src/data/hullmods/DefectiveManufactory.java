package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.hullmods.CompromisedStructure;


public class DefectiveManufactory extends BaseHullMod {

	public static float SPEED_REDUCTION = 0.33333333f;
	public static float DAMAGE_INCREASE = 0.5f;
	
	
	public void applyEffectsToFighterSpawnedByShip(ShipAPI fighter, ShipAPI ship, String id) {
		float effect = ship.getMutableStats().getDynamic().getValue(Stats.DMOD_EFFECT_MULT);
		
		MutableShipStatsAPI stats = fighter.getMutableStats();
		
		stats.getMaxSpeed().modifyMult(id, 1f - SPEED_REDUCTION * effect);
		
		stats.getArmorDamageTakenMult().modifyPercent(id, DAMAGE_INCREASE * 100f * effect);
		stats.getShieldDamageTakenMult().modifyPercent(id, DAMAGE_INCREASE * 100f * effect);
		stats.getHullDamageTakenMult().modifyPercent(id, DAMAGE_INCREASE * 100f * effect);
		
		fighter.setHeavyDHullOverlay();
	}
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		CompromisedStructure.modifyCost(hullSize, stats, id);
	}
	
		
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		float effect = 1f;
		if (ship != null) effect = ship.getMutableStats().getDynamic().getValue(Stats.DMOD_EFFECT_MULT);
		
		if (index == 0) return "" + (int) Math.round(SPEED_REDUCTION * 100f * effect) + "%";
		if (index == 1) return "" + (int) Math.round(DAMAGE_INCREASE * 100f * effect) + "%";
		if (index >= 2) return CompromisedStructure.getCostDescParam(index, 2); 
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		int bays = (int) ship.getMutableStats().getNumFighterBays().getBaseValue();
		return ship != null && bays > 0;
	}

	public String getUnapplicableReason(ShipAPI ship) {
		return "Ship does not have fighter bays";
	}
}




