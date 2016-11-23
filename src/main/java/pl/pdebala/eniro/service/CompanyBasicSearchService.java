package pl.pdebala.eniro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import pl.pdebala.eniro.rpository.SearchHistoryRepository;
import pl.pdebala.eniro.rpository.entity.SearchHistory;
import pl.pdebala.eniro.service.model.Company;
import pl.pdebala.eniro.util.JsonConverter;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

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
    public Set<DeferredResult<Set<Company>>> search(final String commaSeparatedKeys, final String filter) {

        final String[] keyWords = commaSeparatedKeys.split(",");

        final Set<DeferredResult<Set<Company>>> deferredResultSet = new HashSet<DeferredResult<Set<Company>>>();

        Arrays.stream(keyWords)
                .map(key -> asyncRestTemplate.getForEntity((ENIRO_SERVICE_URL + key), String.class))
                .forEach(new Consumer<ListenableFuture<ResponseEntity<String>>>() {
                    @Override
                    public void accept(ListenableFuture<ResponseEntity<String>> future) {
                        System.out.println(" Future: " + future);
                        try {
                            ResponseEntity<String> response = future.get();
                            System.out.println("--- Response obtained ---");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                });

        searchHistoryRepository.save(new SearchHistory(commaSeparatedKeys, filter));

        return deferredResultSet;
    }

    @Override
    public List<SearchHistory> getHistory() {
        return (List<SearchHistory>) searchHistoryRepository.findAll();
    }


    @PostConstruct
    public void initProxy(){
        int portNr = -1;
        try {
            portNr = Integer.parseInt("8080");
        } catch (NumberFormatException e) {
            logger.error("Unable to parse the proxy port number");
        }
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        InetSocketAddress address = new InetSocketAddress("10.56.3.1",portNr);
        Proxy proxy = new Proxy(Proxy.Type.HTTP,address);
        factory.setProxy(proxy);

        ((SimpleClientHttpRequestFactory)asyncRestTemplate.getAsyncRequestFactory()).setProxy(proxy);
    }

}
