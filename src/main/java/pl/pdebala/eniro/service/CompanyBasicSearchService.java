package pl.pdebala.eniro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import pl.pdebala.eniro.rpository.SearchHistoryRepository;
import pl.pdebala.eniro.rpository.entity.SearchHistory;
import pl.pdebala.eniro.service.model.Company;
import pl.pdebala.eniro.util.JsonConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by pdebala on 2016-10-08.
 */
@Service
public class CompanyBasicSearchService implements CompanyBasicSearch {

    private final Logger logger = LoggerFactory.getLogger(CompanyBasicSearchService.class.getName());

    @Value("${eniro.service.url}")
    private String ENIRO_SERVICE_URL;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    private JsonConverter jsonFilter;

    @Override
    public Set<Company> search(final String commaSeparatedKeys, final String filter) {

        String[] keyWords = commaSeparatedKeys.split(",");

        Set<Company> result = new HashSet<>();

        Arrays.stream(keyWords)
                .map(key -> asyncRestTemplate.getForEntity((ENIRO_SERVICE_URL + key), String.class))
                .forEach(future -> {
                    try {
                        String responseBody = future.get().getBody();
                        result.addAll(jsonFilter.convert(responseBody, "adverts", Company.class, filter));
                    } catch (InterruptedException e) {
                        logger.error("Exception while trying to obtain response data." + e);

                    } catch (ExecutionException e) {
                        logger.error("Exception while trying to obtain response data." + e);
                    }
                });

        searchHistoryRepository.save(new SearchHistory(commaSeparatedKeys, filter));

        return result;
    }

    @Override
    public List<SearchHistory> getHistory() {
        return (List<SearchHistory>) searchHistoryRepository.findAll();
    }


}
