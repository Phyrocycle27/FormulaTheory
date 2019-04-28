package tk.hiddenname.probe.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Subject {
   private long id;
   private String name;
   private int numOfFormulas, drawableId, color;
   // убрать после добавления бд
   private ArrayList<Section> sections;
   private Bitmap bitmap;

   // устарело
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

   public Subject() {
   }

   public void setName(String name) {
	  this.name = name;
   }

   public void setNumOfFormulas(int numOfFormulas) {
	  this.numOfFormulas = numOfFormulas;
   }

   public void setDrawableId(int drawableId) {
	  this.drawableId = drawableId;
   }

   public void setColor(int color) {
	  this.color = color;
   }

   public long getId() {
	  return id;
   }

   public void setId(long id) {
	  this.id = id;
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

   // убрать после добавления бд
   public ArrayList<Section> getSections() {
	  return sections;
   }

   @NonNull
   @Override
   public String toString() {
	  return "Subject{" +
					 "name='" + name + '\'' +
					 ", numOfFormulas=" + numOfFormulas +
					 ", drawableId=" + drawableId +
					 ", color=" + color +
					 ", bitmap=" + bitmap +
					 '}';
   }
}