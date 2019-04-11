package tk.hiddenname.probe.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Units {

   private Map<String, Unit> units = new HashMap<>();

   public void addUnit(String letter, Units.Unit unit) {
	  units.put(letter, unit);
	  List<Map.Entry<String, Double>> list = new ArrayList<>(unit.getUnits().entrySet());
	  Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
		 @Override
		 public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
			return a.getValue().compareTo(b.getValue());
		 }
	  });
   }

   public String[] getUnitsByLetter(String letter) {
	  return units.get(letter).getUnits().keySet().toArray(new String[0]);
   }


   public class Unit {

	  private Map<String, Double> unit;

	  public Unit(HashMap<String, Double> unit) {
		 this.unit = unit;
	  }

	  private Map<String, Double> getUnits() {
		 return unit;
	  }

	  private double getCoef(String nameOfUnit) {
		 return unit.get(nameOfUnit);
	  }

   }
}
