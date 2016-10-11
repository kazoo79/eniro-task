package pl.pdebala.eniro.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pdebala on 2016-10-09.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @JsonProperty("streetName")
    private String streetName;

    @JsonProperty("postCode")
    private String postCode;

    @JsonProperty("postArea")
    private String postArea;

    @JsonProperty("postBox")
    private String postBox;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostArea() {
        return postArea;
    }

    public void setPostArea(String postArea) {
        this.postArea = postArea;
    }

    public String getPostBox() {
        return postBox;
    }

    public void setPostBox(String postBox) {
        this.postBox = postBox;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", postCode='" + postCode + '\'' +
                ", postArea='" + postArea + '\'' +
                ", postBox='" + postBox + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (streetName != null ? !streetName.equals(address.streetName) : address.streetName != null) return false;
        if (postCode != null ? !postCode.equals(address.postCode) : address.postCode != null) return false;
        if (postArea != null ? !postArea.equals(address.postArea) : address.postArea != null) return false;
        return postBox != null ? postBox.equals(address.postBox) : address.postBox == null;

    }

    @Override
    public int hashCode() {
        int result = streetName != null ? streetName.hashCode() : 0;
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (postArea != null ? postArea.hashCode() : 0);
        result = 31 * result + (postBox != null ? postBox.hashCode() : 0);
        return result;
    }
}
