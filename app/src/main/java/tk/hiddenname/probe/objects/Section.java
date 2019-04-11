package tk.hiddenname.probe.objects;

import android.content.Context;

import java.util.ArrayList;

public class Section {
   private String name;
   private int numOfFormulas = 0;
   private ArrayList<Formula> formulas;

   public Section(int nameId, ArrayList<Formula> formulas, Context context) {
	  name = context.getResources().getString(nameId);
	  this.formulas = formulas;
	  try {
		 for (Formula formula : formulas) numOfFormulas += formula.getNumOfFormulas();
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
   }


   public String getName() {
	  return name;
   }

   public int getNumOfFormulas() {
	  return numOfFormulas;
   }

   public ArrayList<Formula> getFormulas() {
	  return formulas;
   }
}