package pl.pdebala.eniro.service;

import pl.pdebala.eniro.rpository.entity.SearchHistory;
import pl.pdebala.eniro.service.model.Company;

import java.util.List;
import java.util.Set;

/**
 * Created by pdebala on 2016-10-08.
 */
public interface CompanyBasicSearch {

    Set<Company> search(final String commaSeparatedKeys, final String filter);

    List<SearchHistory> getHistory();
}
