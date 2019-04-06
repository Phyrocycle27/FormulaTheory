package tk.hiddenname.probe.objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Formula implements Parcelable {

   private static final String delimeters = "[=+\\-*(/)\\s]";
   private String name, formula;
   private int numOfFormulas;
   private String[] formulas, components;

   public Formula(int formulaArray, Context context) {
	  String formulaArr[];
	  formulaArr = context.getResources().getStringArray(formulaArray);
	  int len = formulaArr.length - 1;
	  numOfFormulas = len;
	  name = formulaArr[0];
	  formulas = new String[len];
	  System.arraycopy(formulaArr, 1, formulas, 0, len);
	  formula = formulas[0];
	  components = createComponets(formula);
	  Log.d("COMPONENTS", Arrays.toString(components));
   }

   private Formula(Parcel in) {
	  name = in.readString();
	  formula = in.readString();
	  numOfFormulas = in.readInt();
	  formulas = in.createStringArray();
	  components = in.createStringArray();
   }

   private String[] createComponets(String str) {
	  ArrayList<String> strings = new ArrayList<>();
	  for (String component : str.split(delimeters))
		 if (!component.equals("")) strings.add(component);
	  return strings.toArray(new String[0]);
   }

   public String[] getComponents() {
	  return components;
   }

   public String getComponentByIndex(int index) {
	  return components[index];
   }

   public String getName() {
	  return name;
   }

   public String getFormula() {
	  return formula;
   }

   int getNumOfFormulas() {
	  return numOfFormulas;
   }

   public String[] getFormulas() {
	  return formulas;
   }

   public double solve(HashMap<String, String> map) {
	  // Неизвестный компонент и целевая формула
	  String unknown = "", targetFormula = "";
	  // Определяем неизвестный компонент
	  for (String key : map.keySet())
	     // Проверяем значение компонента в таблице по его ключу
		 if (map.get(key).equals("")) unknown = key;
	  // Находим формулу, подходящую для нахождения значения неизвестного компонента
	  for (String formula : formulas)
		 if (formula.startsWith(unknown + " ")) targetFormula = formula;
	  // Заменяем буквы в формуле на известные нам значения и составляем выражение
	  String[] str = targetFormula.split("[=]");
	  targetFormula = str[1].substring(1);
	  for (String component : components)
		 targetFormula = targetFormula.replace(component, map.get(component));
	  // Вывод выражения в Log
	  Log.d("Formula", "Current target formula is: " + targetFormula);
	  // *****************Передаём полученное выражение на вычисление в новый поток***********************
	  // Создаём внутренний локальный поток для вычисления
	  final String finalExpression = targetFormula;
	  class SolveThread extends Thread {
		 @Override
		 public void run() {
			super.run();
		 }

		 private double calculate() {
			Expression expression = new Expression(finalExpression);
			return expression.calculate();
		 }
	  }
	  // Запускаем поток вычисления
	  SolveThread st = new SolveThread();
	  st.start();
	  // Возвращаем ответ на выражение
	  return st.calculate();
   }

   public static final Parcelable.Creator<Formula> CREATOR = new Parcelable.Creator<Formula>() {
	  @Override
	  public Formula createFromParcel(Parcel in) {
		 return new Formula(in);
	  }

	  @Override
	  public Formula[] newArray(int size) {
		 return new Formula[size];
	  }
   };

   @Override
   public int describeContents() {
	  return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
	  dest.writeString(name);
	  dest.writeString(formula);
	  dest.writeInt(numOfFormulas);
	  dest.writeStringArray(formulas);
	  dest.writeStringArray(components);
   }
}