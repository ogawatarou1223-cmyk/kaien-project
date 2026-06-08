package model;

import java.sql.Date;

public class ItemDeleteDto{
	//商品一覧用DTO
	public String itemId;
	public String itemName;
	public int    price;
	public String itemCategory;
	public String publicStatus;
	public String itemDescription;
	public Date   releaseDate;
	
	//getter/setterの定義
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public String getPublicStatus() {
		return publicStatus;
	}
	public void setPublicStatus(String publicStatus) {
		this.publicStatus = publicStatus;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date date) {
		this.releaseDate = date;
	}
	
	
}