package data.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;

import java.util.HashSet;
import java.util.Set;

public class AdvancedEfficiency2 extends BaseLogisticsHullMod {
	public static final float MAINTENANCE_MULT = 0.5f;

	private static final Set<String> BLOCKED_HULLMODS = new HashSet<>();
	static {
		BLOCKED_HULLMODS.add("AdvancedEfficiency4");
		BLOCKED_HULLMODS.add("AdvancedEfficiency3");
		BLOCKED_HULLMODS.add("AdvancedEfficiency1");
		BLOCKED_HULLMODS.add("efficiency_overhaul");
	}

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getSuppliesPerMonth().modifyMult(id, MAINTENANCE_MULT);
	}

	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + (int) Math.round((1f - MAINTENANCE_MULT) * 100f) + "%";
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		for (String tmp : BLOCKED_HULLMODS) {
			if (ship.getVariant().getHullMods().contains(tmp)) {
				return false;
			}
		}
		return true;
	}

	public String getUnapplicableReason(ShipAPI ship) {
		for (String tmp : BLOCKED_HULLMODS) {
			if (ship.getVariant().getHullMods().contains(tmp)) {
				return ("Incompatible with other efficiency hullmods");
			}
		}
		return null;
	}
}
