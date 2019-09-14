package collector;

import java.io.Serializable;
import java.util.Objects;

public class Collector implements Serializable {
    private int id;
    private String brand;
    private String country;
    private int price;

    public Collector(int id, String brand, String country, int price) {
        this.id = id;
        this.brand = brand;
        this.country = country;
        this.price = price;
    }

    public int getId() {return id;}

    public String getBrand() {
        return brand;
    }

    public String getCountry() {
        return country;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {this.id = id;}

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collector collector = (Collector) o;
        return id == collector.id &&
                price == collector.price &&
                Objects.equals(brand, collector.brand) &&
                Objects.equals(country, collector.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, country, price);
    }
}


