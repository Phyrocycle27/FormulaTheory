package tk.hiddenname.probe.objects;

import java.util.HashMap;

public class Units {

   private HashMap<String, Unit> units = new HashMap<>();

   public void addUnit(String letter, Unit unit) {
	  units.put(letter, unit);
   }

   public String[] getUnitsByLetter(String letter) {
	  return units.get(letter).getUnits().keySet().toArray(new String[0]);
   }


   public class Unit {

	  private HashMap<String, Double> unit;

	  public Unit(HashMap<String, Double> unit) {
		 this.unit = unit;
	  }

	  private HashMap<String, Double> getUnits() {
		 return this.unit;
	  }

	  private double getCoeff(String nameOfUnit) {
		 return unit.get(nameOfUnit);
	  }

   }
}
