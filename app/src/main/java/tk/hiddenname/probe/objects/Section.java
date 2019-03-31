package tk.hiddenname.probe.objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Section implements Parcelable {
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

   private Section(Parcel in) {
	  name = in.readString();
	  numOfFormulas = in.readInt();
	  formulas = in.createTypedArrayList(Formula.CREATOR);
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

   public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
	  @Override
	  public Section createFromParcel(Parcel in) {
		 return new Section(in);
	  }

	  @Override
	  public Section[] newArray(int size) {
		 return new Section[size];
	  }
   };

   @Override
   public int describeContents() {
	  return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
	  dest.writeString(name);
	  dest.writeInt(numOfFormulas);
	  dest.writeTypedList(formulas);
   }
}