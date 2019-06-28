package com.example.readdata;

public class listItem {

    private String Name;
    private String Price;
    private String imageUrl;

    private boolean isSelected;

    public listItem() {
    }

    public listItem(String name, String price, String imageUrl) {
        Name = name;
        Price = price;
    }

    public listItem(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getImageUrl() { return imageUrl; }

    public boolean isSelected(){ return isSelected; }

    public void setSelected(boolean selected){ isSelected = selected; }
}

