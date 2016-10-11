package pl.pdebala.eniro.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by pdebala on 2016-10-08.
 */
public class SearchCriteria {

    @NotNull
    @Pattern(regexp = "[-A-Za-z0-9]+(,[-A-Za-z0-9]+)*")
    private String commaSeparatedKeys;

    private String filter;

    public String getCommaSeparatedKeys() {
        return commaSeparatedKeys;
    }

    public void setCommaSeparatedKeys(String commaSeparatedKeys) {
        this.commaSeparatedKeys = commaSeparatedKeys;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
