package pl.pdebala.eniro.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pdebala on 2016-10-08.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

    @JsonProperty("eniroId")
    private Integer eniroId;

    @JsonProperty("companyInfo")
    private CompanyInfo companyInfo;

    @JsonProperty("address")
    private Address address;

    public Integer getEniroId() {
        return eniroId;
    }

    public void setEniroId(Integer eniroId) {
        this.eniroId = eniroId;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "eniroId=" + eniroId +
                ", companyInfo=" + companyInfo +
                ", address=" + address +
                "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (eniroId != null ? !eniroId.equals(company.eniroId) : company.eniroId != null) return false;
        if (companyInfo != null ? !companyInfo.equals(company.companyInfo) : company.companyInfo != null) return false;
        return address != null ? address.equals(company.address) : company.address == null;

    }

    @Override
    public int hashCode() {
        int result = eniroId != null ? eniroId.hashCode() : 0;
        result = 31 * result + (companyInfo != null ? companyInfo.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
