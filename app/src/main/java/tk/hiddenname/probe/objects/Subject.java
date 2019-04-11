package tk.hiddenname.probe.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Subject {
   private String name;
   private int numOfFormulas, drawableId, color;
   private Bitmap bitmap;
   private ArrayList<Section> sections;

   public Subject(int nameId, int drawableID, int color, ArrayList<Section> sections, Context context) {
	  this.color = color;
	  name = context.getResources().getString(nameId);
	  bitmap = BitmapFactory.decodeResource(context.getResources(), drawableID);
	  this.sections = sections;
	  this.drawableId = drawableID;
	  try {
		 for (Section section : sections) numOfFormulas += section.getNumOfFormulas();
	  } catch (NullPointerException e) {
		 e.printStackTrace();
	  }
   }

   public String getName() {
	  return name;
   }

   public int getColor() {
	  return color;
   }

   public int getDrawableId() {
	  return drawableId;
   }

   public Bitmap getDrawable() {
	  return bitmap;
   }

   public int getNumOfFormulas() {
	  return numOfFormulas;
   }

   public ArrayList<Section> getSections() {
	  return sections;
   }
}