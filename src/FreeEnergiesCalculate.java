import java.util.ArrayList;
import java.util.List;

import cal.HairpinLoopCalculator;
import cal.InternalLoopCalculator;
import cal.MultiDomainCalculator;
import cal.MultibrandchedCalculator;
import cal.StackLoopCalculator;
import model.BulgeLoop;
import model.HairpinLoop;
import model.InternalLoop;
import model.MultiBranchedLoop;
import model.StackedLoop;

public class FreeEnergiesCalculate {

	private String enclus;
	private String structure;
	private List<Character> struc = new ArrayList<Character>();
	private List<Character> strucEnclu = new ArrayList<Character>();
	private List<Boolean> checkStruc = new ArrayList<Boolean>();
	private String subStructure;
	private String subEnclu;
	private String bonusForHairpin;
	private float results = 0;
	
	public FreeEnergiesCalculate() {
	}
	
	public FreeEnergiesCalculate(String enclus, String structure) {
		this.enclus = enclus.toUpperCase();
		this.structure = structure;
	}
	
	public void setEnclu(String enclus) {
		this.enclus = enclus.toUpperCase();
	}
	
	public void setStructure(String structure) {
		this.structure = structure;
	}
	
	public String getEnclu() {
		if (this.enclus == null) return "";
		return enclus;
	}
	
	public String getStructure() {
		if (this.structure == null) return "";
		return structure;
	}
	
	public float calculateFreeEnergyDetails() {
		
		for (int j=0; j<structure.length(); j++) {
			struc.add(structure.charAt(j));
			strucEnclu.add(this.enclus.charAt(j));
			checkStruc.add(false);
		}
		
		int i = 0;
		
		while (i < struc.size()) {
			if (struc.get(i) == ')') {
				 for (int j=i-1; j>=0; j--) {
					 if (struc.get(j) == '(' && checkStruc.get(j) == false) {
						 subStructure = "";
						 subEnclu = "";
						 if (j > 1) bonusForHairpin = ""+strucEnclu.get(j-2)+strucEnclu.get(j-1);
						 else bonusForHairpin = "";
						 for (int k=j; k<=i; k++) {
							 subStructure += struc.get(k);
							 subEnclu += strucEnclu.get(k);
						 }
						 int tmp = j+1;
						 int tmp1 = i;
						 for (int k=j+1; k<tmp1; k++) {
							 struc.remove(tmp);
							 strucEnclu.remove(tmp);
							 checkStruc.remove(tmp);
							 i--;
						 }
						 checkStruc.set(j, true);
						 System.out.println(subStructure+"---"+subEnclu);
						 
						 break;
					 }
				 }
				 
				 if (subStructure.length() != 0) {
					 
					 if (BulgeLoop.shareInstance().isBulgeLoop(subStructure)) {
						 float temp = InternalLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 System.out.println("Bulge Loop ----> "+temp);
						 results += temp;
					 }else if (InternalLoop.shareInstance().isInternalLoop(subStructure)) {
						 float temp = InternalLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 System.out.println("Internal Loop ----> "+temp);
						 results += temp;
					 }else if (MultiBranchedLoop.shareInstance().isMultiBrandedLoop(subStructure)) {
						 float temp = MultibrandchedCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 System.out.println("MultiBranched Loop ----> "+temp);
						 results += temp;
					 }else if (HairpinLoop.shareInstance().isHairpinLoop(subStructure)) {
						 float temp = HairpinLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu, bonusForHairpin);
						 System.out.println("Bonus: "+bonusForHairpin);
						 System.out.println("Hairpin Loop ----> "+temp);
						 results += temp;
					 }else if (StackedLoop.shareInstance().isStackedLoop(subStructure)) {
						 float temp = StackLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 System.out.println("Stacked Loop ----> "+temp);
						 results += temp;
					 }
					 
				 }
				 
			}
			i++;
		}
		
		System.out.println("Muti Domain:");
		System.out.println("Struc: "+getStringRepresentation(struc));
		System.out.println("Enclu: "+getStringRepresentation(strucEnclu));
		float temp = MultiDomainCalculator.shareInstance().computeDeltaG(getStringRepresentation(struc), getStringRepresentation(strucEnclu));
		System.out.println("Multi Domain: ----> "+temp);
		results += temp;
		results = (float)Math.round(results*10)/10;
		
		System.out.println("Result = "+results);
		
		return this.results;
	}
	
	public float calculateFreeEnergyDetails(String enclus, String structure) {
		
		this.enclus = enclus.toUpperCase();
		this.structure = structure;
		
		for (int j=0; j<structure.length(); j++) {
			struc.add(structure.charAt(j));
			strucEnclu.add(this.enclus.charAt(j));
			checkStruc.add(false);
		}
		
		int i = 0;
		
		while (i < struc.size()) {
			if (struc.get(i) == ')') {
				 for (int j=i-1; j>=0; j--) {
					 if (struc.get(j) == '(' && checkStruc.get(j) == false) {
						 subStructure = "";
						 subEnclu = "";
						 if (j > 1) bonusForHairpin = ""+strucEnclu.get(j-2)+strucEnclu.get(j-1);
						 else bonusForHairpin = "";
						 for (int k=j; k<=i; k++) {
							 subStructure += struc.get(k);
							 subEnclu += strucEnclu.get(k);
						 }
						 int tmp = j+1;
						 int tmp1 = i;
						 for (int k=j+1; k<tmp1; k++) {
							 struc.remove(tmp);
							 strucEnclu.remove(tmp);
							 checkStruc.remove(tmp);
							 i--;
						 }
						 checkStruc.set(j, true);
						 System.out.println(subStructure+"---"+subEnclu);
						 
						 break;
					 }
				 }
				 
				 if (subStructure.length() != 0) {
					 
					 if (BulgeLoop.shareInstance().isBulgeLoop(subStructure)) {
						 float temp = InternalLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 System.out.println("Bulge Loop ----> "+temp);
						 results += temp;
					 }else if (InternalLoop.shareInstance().isInternalLoop(subStructure)) {
						 float temp = InternalLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 System.out.println("Internal Loop ----> "+temp);
						 results += temp;
					 }else if (MultiBranchedLoop.shareInstance().isMultiBrandedLoop(subStructure)) {
						 float temp = MultibrandchedCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 System.out.println("MultiBranched Loop ----> "+temp);
						 results += temp;
					 }else if (HairpinLoop.shareInstance().isHairpinLoop(subStructure)) {
						 float temp = HairpinLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu, bonusForHairpin);
						 System.out.println("Bonus: "+bonusForHairpin);
						 System.out.println("Hairpin Loop ----> "+temp);
						 results += temp;
					 }else if (StackedLoop.shareInstance().isStackedLoop(subStructure)) {
						 float temp = StackLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 System.out.println("Stacked Loop ----> "+temp);
						 results += temp;
					 }
					 
				 }
				 
			}
			i++;
		}
		
		System.out.println("Muti Domain:");
		System.out.println("Struc: "+getStringRepresentation(struc));
		System.out.println("Enclu: "+getStringRepresentation(strucEnclu));
		float temp = MultiDomainCalculator.shareInstance().computeDeltaG(getStringRepresentation(struc), getStringRepresentation(strucEnclu));
		System.out.println("Multi Domain: ----> "+temp);
		results += temp;
		results = (float)Math.round(results*10)/10;
		
		System.out.println("Result = "+results);
		
		return this.results;
	}
	
	public float calculateFreeEnergy() {
		
		for (int j=0; j<structure.length(); j++) {
			struc.add(structure.charAt(j));
			strucEnclu.add(this.enclus.charAt(j));
			checkStruc.add(false);
		}
		
		int i = 0;
		
		while (i < struc.size()) {
			if (struc.get(i) == ')') {
				 for (int j=i-1; j>=0; j--) {
					 if (struc.get(j) == '(' && checkStruc.get(j) == false) {
						 subStructure = "";
						 subEnclu = "";
						 if (j > 1) bonusForHairpin = ""+strucEnclu.get(j-2)+strucEnclu.get(j-1);
						 else bonusForHairpin = "";
						 for (int k=j; k<=i; k++) {
							 subStructure += struc.get(k);
							 subEnclu += strucEnclu.get(k);
						 }
						 int tmp = j+1;
						 int tmp1 = i;
						 for (int k=j+1; k<tmp1; k++) {
							 struc.remove(tmp);
							 strucEnclu.remove(tmp);
							 checkStruc.remove(tmp);
							 i--;
						 }
						 checkStruc.set(j, true);
						 
						 break;
					 }
				 }
				 
				 if (subStructure.length() != 0) {
					 
					 if (BulgeLoop.shareInstance().isBulgeLoop(subStructure)) {
						 float temp = InternalLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 results += temp;
					 }else if (InternalLoop.shareInstance().isInternalLoop(subStructure)) {
						 float temp = InternalLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 results += temp;
					 }else if (MultiBranchedLoop.shareInstance().isMultiBrandedLoop(subStructure)) {
						 float temp = MultibrandchedCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 results += temp;
					 }else if (HairpinLoop.shareInstance().isHairpinLoop(subStructure)) {
						 float temp = HairpinLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu, bonusForHairpin);
						 results += temp;
					 }else if (StackedLoop.shareInstance().isStackedLoop(subStructure)) {
						 float temp = StackLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 results += temp;
					 }
					 
				 }
				 
			}
			i++;
		}

		float temp = MultiDomainCalculator.shareInstance().computeDeltaG(getStringRepresentation(struc), getStringRepresentation(strucEnclu));
		results += temp;
		results = (float)Math.round(results*10)/10;
		
		return this.results;
	}
	
	public float calculateFreeEnergy(String enclus, String structure) {
		
		this.enclus = enclus;
		this.structure = structure;
		
		for (int j=0; j<structure.length(); j++) {
			struc.add(structure.charAt(j));
			strucEnclu.add(this.enclus.charAt(j));
			checkStruc.add(false);
		}
		
		int i = 0;
		
		while (i < struc.size()) {
			if (struc.get(i) == ')') {
				 for (int j=i-1; j>=0; j--) {
					 if (struc.get(j) == '(' && checkStruc.get(j) == false) {
						 subStructure = "";
						 subEnclu = "";
						 if (j > 1) bonusForHairpin = ""+strucEnclu.get(j-2)+strucEnclu.get(j-1);
						 else bonusForHairpin = "";
						 for (int k=j; k<=i; k++) {
							 subStructure += struc.get(k);
							 subEnclu += strucEnclu.get(k);
						 }
						 int tmp = j+1;
						 int tmp1 = i;
						 for (int k=j+1; k<tmp1; k++) {
							 struc.remove(tmp);
							 strucEnclu.remove(tmp);
							 checkStruc.remove(tmp);
							 i--;
						 }
						 checkStruc.set(j, true);
						 
						 break;
					 }
				 }
				 
				 if (subStructure.length() != 0) {
					 
					 if (BulgeLoop.shareInstance().isBulgeLoop(subStructure)) {
						 float temp = InternalLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 results += temp;
					 }else if (InternalLoop.shareInstance().isInternalLoop(subStructure)) {
						 float temp = InternalLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 results += temp;
					 }else if (MultiBranchedLoop.shareInstance().isMultiBrandedLoop(subStructure)) {
						 float temp = MultibrandchedCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 results += temp;
					 }else if (HairpinLoop.shareInstance().isHairpinLoop(subStructure)) {
						 float temp = HairpinLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu, bonusForHairpin);
						 results += temp;
					 }else if (StackedLoop.shareInstance().isStackedLoop(subStructure)) {
						 float temp = StackLoopCalculator.shareInstance().computeDeltaG(subStructure, subEnclu);
						 results += temp;
					 }
					 
				 }
				 
			}
			i++;
		}

		float temp = MultiDomainCalculator.shareInstance().computeDeltaG(getStringRepresentation(struc), getStringRepresentation(strucEnclu));
		results += temp;
		results = (float)Math.round(results*10)/10;
		
		return this.results;
	}
	
	private String getStringRepresentation(List<Character> struc2)
	{    
	    StringBuilder builder = new StringBuilder(struc2.size());
	    for(Character ch: struc2)
	    {
	        builder.append(ch);
	    }
	    return builder.toString();
	}

	//-44.7		structure = ".(((((((((...(((.((((((...(((....))).))))))..)))....))))...)))))..((..(((.....)))((((((((..((.......)).........))))))))))..";
	//-44.9		structure = ".(((((((((...(((.((((((...(((....))).))))))..)))....))))...)))))..((..(.((((..(((......))).((.......)).........)))).)..))..";
	//-43.4		structure = "(((((.((((...(((.((((((...(((....))).))))))..)))....))))....((..(((..(((......(((......)))....)))...)))..))....))))).......";
//			structure = "..(((.((((...(((.((((((...(((....))).))))))..)))....))))...((((..(((..(((.....)))..))).)).))..)))....(((((.((....)).)).))).";
//			enclu  =    "CCGGGGGGGCGAGGCACAGGGAUCUAGAGUGAACUCCAUCCCUCAUGCAGUAGCCCAGACUCCGCGGCAGGGCGAGAAGCCGAGCCAGGCAGGACCCAUACCGUAGACACACUUGGCUCGCGU";
			
			
			
//			enclu =     "ACAUGGGGAUAAGGGCAGGCGGUGAAUGCCUUGGCUCUCGGAGGCGAAGAAGGACGUGAUAAGCUGCGAUAAGCCCGGCGUAGGCGCAAAUAGCCGUUAAUACCGGGGUUUCCGAAUGGGGCAACCCGCCGGGAGUAAUUCCGGCAUCUCUUGAAAGAGGGAGGCGAACGUGGGGAACUGAAACAUCUCAGUACCUGCAGGAAAAAAAAAAAAAAAAAAAA";
	//-72.1		structure = "...(((...(((.(((..(((.(..(((((..((((.(((..(((.................))).)))..)))).)))))..)))).....))).)))...)))...(((((....(((....)))(((((((....))))))).((((((....)))))).......(..((..(((((......))))).))..).))))).................";
	//-73.4		structure = "....(((....((((((.........)))))).((((((((((((.........(((........))).....(((((....(((.......))).......)))))))))))))...))))..)))(((((((....))))))).((((((....)))))).......(..((..(((((......))))).))..).......................";
	//-73.6		structure = "...........((((((((((.....)))))..)))))(((((((.........(((........))).....(((((....(((.......))).......))))))))))))...(((....)))(((((((....))))))).((((((....)))))).......(..((..(((((......))))).))..).......................";
	//-70.4		structure = "..............((((((((((.(((((..((((.(((..(((.................))).)))..)))).))))).(((.......))).....))))).(((((((....(((....)))(((((((....))))))).((((((....))))))..........)))))))..............))))).......................";	
	
	
}
