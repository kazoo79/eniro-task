package pl.pdebala.eniro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.AsyncRestTemplate;
import pl.pdebala.eniro.rpository.SearchHistoryRepository;
import pl.pdebala.eniro.rpository.entity.SearchHistory;
import pl.pdebala.eniro.service.model.Company;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    @Override
    public Set<Company> search(final String commaSeparatedKeys, final String filter) {

        String[] keyWords = commaSeparatedKeys.split(",");

        Set<Company> result = new HashSet<>();

        Arrays.stream(keyWords)
                .map(key -> asyncRestTemplate.getForEntity((ENIRO_SERVICE_URL + key), String.class))
                .forEach(future -> {
                    try {
                        String responseBody = future.get().getBody();
                        result.addAll(doFilter(responseBody, filter));
                    } catch (InterruptedException e) {
                        logger.error("Exception while trying obtain response data." + e);

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        logger.error("Exception while trying obtain response data." + e);
                    }
                });

        searchHistoryRepository.save(new SearchHistory(commaSeparatedKeys, filter));

        return result;
    }

    @Override
    public List<SearchHistory> getHistory() {
        return (List<SearchHistory>) searchHistoryRepository.findAll();
    }

    private Set<Company> doFilter(final String json, final String filter) {
        if (StringUtils.isEmpty(json) || StringUtils.isEmpty(filter)) {
            return new HashSet<>();
        }
        ObjectMapper mapper = new ObjectMapper();
        Map obj = null;
        try {
            obj = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map> advertsMap = (List<Map>) obj.get("adverts");
        return advertsMap == null ? new HashSet<>() : advertsMap.stream()
                .filter(advert -> contains(advert, filter))
                .map(advert -> mapper.convertValue(advert, Company.class))
                .collect(Collectors.toSet());
    }

    private static boolean contains(Map root, String filter) {
        return StringUtils.isEmpty(filter) || root.values().stream().anyMatch(o -> {
            if (o != null) {
                if (o instanceof LinkedHashMap) {
                    if (contains((LinkedHashMap) o, filter)) {
                        return true;
                    }
                } else if (o instanceof String) {
                    if (((String) o).contains(filter)) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

}
