package pl.pdebala.eniro.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pdebala on 2016-10-09.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyInfo {

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("orgNumber")
    private String orgNumber;

    @JsonProperty("companyText")
    private String companyText;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getCompanyText() {
        return companyText;
    }

    public void setCompanyText(String companyText) {
        this.companyText = companyText;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "companyName='" + companyName + '\'' +
                ", orgNumber='" + orgNumber + '\'' +
                ", companyText='" + companyText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyInfo that = (CompanyInfo) o;

        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (orgNumber != null ? !orgNumber.equals(that.orgNumber) : that.orgNumber != null) return false;
        return companyText != null ? companyText.equals(that.companyText) : that.companyText == null;

    }

    @Override
    public int hashCode() {
        int result = companyName != null ? companyName.hashCode() : 0;
        result = 31 * result + (orgNumber != null ? orgNumber.hashCode() : 0);
        result = 31 * result + (companyText != null ? companyText.hashCode() : 0);
        return result;
    }
}
