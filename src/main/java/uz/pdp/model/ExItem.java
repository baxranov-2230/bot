package uz.pdp.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.List;

@Generated("com.asif.gsonpojogenerator")
public class ExItem implements Serializable {

	@SerializedName("text")
	private String text;

	@SerializedName("tr")
	private List<TrItem> tr;

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setTr(List<TrItem> tr){
		this.tr = tr;
	}

	public List<TrItem> getTr(){
		return tr;
	}

	@Override
 	public String toString(){
		return 
			"ExItem{" + 
			"text = '" + text + '\'' + 
			",tr = '" + tr + '\'' + 
			"}";
		}
}