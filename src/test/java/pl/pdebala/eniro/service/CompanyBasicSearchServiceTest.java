package pl.pdebala.eniro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import pl.pdebala.eniro.rpository.SearchHistoryRepository;
import pl.pdebala.eniro.rpository.entity.SearchHistory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by pdebala on 2016-10-10.
 */
@RunWith(SpringRunner.class)
public class CompanyBasicSearchServiceTest {

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @Mock
    private AsyncRestTemplate asyncRestTemplate;

    @InjectMocks
    private CompanyBasicSearchService companyBasicSearchService;


    private final String ENIRO_SERVICE_URL = "http://eniro-service/";
    private final SearchHistory SEARCH_HISTORY_OBJECT = new SearchHistory("key_word", "filter");
    private final List<String> KEY_WORDS = Arrays.asList(new String[]{"key_word_one", "key_word_two"});


    @Test
    public void shouldCallSearchHistoryRepositoryAndReturnResult() {
        // given
        when(searchHistoryRepository.findAll()).thenReturn(Arrays.asList(new SearchHistory[]{SEARCH_HISTORY_OBJECT}));

        // when
        final List<SearchHistory> result = companyBasicSearchService.getHistory();

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(SEARCH_HISTORY_OBJECT, result.get(0));
        verify(searchHistoryRepository).findAll();
    }

    @Test
    public void shouldNotInvokeDoFilerFiNoFilterWordGiven() {
        // given
        ReflectionTestUtils.setField(companyBasicSearchService, "ENIRO_SERVICE_URL", ENIRO_SERVICE_URL);
        when(asyncRestTemplate.getForEntity(ENIRO_SERVICE_URL + KEY_WORDS.get(0), String.class)).thenReturn((ListenableFuture<ResponseEntity<String>>) new Object());

        // when
        companyBasicSearchService.getHistory();

        // then
    }

    @Test
    public void shouldInvokeForeignServiceAndReturnResult() throws ExecutionException, InterruptedException {
        // given
        ReflectionTestUtils.setField(companyBasicSearchService, "ENIRO_SERVICE_URL", ENIRO_SERVICE_URL);

        ResponseEntity<String> responseMock = mock(ResponseEntity.class);
        ListenableFuture<ResponseEntity<String>> featurMock = mock(ListenableFuture.class);
        when(featurMock.get()).thenReturn(responseMock);
        when(responseMock.getBody()).thenReturn("");

        when(asyncRestTemplate.getForEntity(ENIRO_SERVICE_URL + KEY_WORDS.get(0), String.class)).thenReturn(featurMock);

        // when
        companyBasicSearchService.search(KEY_WORDS.get(0), null);

        // then
        verify(asyncRestTemplate, atLeastOnce()).getForEntity(ENIRO_SERVICE_URL + KEY_WORDS.get(0), String.class);
        verify(searchHistoryRepository, atLeastOnce()).save(any(SearchHistory.class));
    }

    @Test
    public void shouldInvokeForeignForEachKeyWordAndReturnResult() throws ExecutionException, InterruptedException {
        // given
        ReflectionTestUtils.setField(companyBasicSearchService, "ENIRO_SERVICE_URL", ENIRO_SERVICE_URL);

        ResponseEntity<String> responseMock = mock(ResponseEntity.class);
        when(responseMock.getBody()).thenReturn("");

        ListenableFuture<ResponseEntity<String>> featurMock = mock(ListenableFuture.class);
        when(featurMock.get()).thenReturn(responseMock);

        when(asyncRestTemplate.getForEntity(ENIRO_SERVICE_URL + KEY_WORDS.get(0), String.class)).thenReturn(featurMock);
        when(asyncRestTemplate.getForEntity(ENIRO_SERVICE_URL + KEY_WORDS.get(1), String.class)).thenReturn(featurMock);

        // when
        companyBasicSearchService.search(KEY_WORDS.toString(), null);

        // then
        verify(asyncRestTemplate, times(2)).getForEntity(anyString(), (Class<String>)anyObject());
        verify(searchHistoryRepository, atLeastOnce()).save(any(SearchHistory.class));
    }

    @Test
    public void shouldHandleTheInterruptedException() throws ExecutionException, InterruptedException {
        // given
        ReflectionTestUtils.setField(companyBasicSearchService, "ENIRO_SERVICE_URL", ENIRO_SERVICE_URL);

        ResponseEntity<String> responseMock = mock(ResponseEntity.class);
        ListenableFuture<ResponseEntity<String>> featurMock = mock(ListenableFuture.class);
        when(featurMock.get()).thenThrow(InterruptedException.class);

        when(asyncRestTemplate.getForEntity(ENIRO_SERVICE_URL + KEY_WORDS.get(0), String.class)).thenReturn(featurMock);

        // when
        companyBasicSearchService.search(KEY_WORDS.get(0), null);

        // then
        verify(searchHistoryRepository, never()).save(anyCollection());
    }

    @Test
    public void shouldHandleTheExecutionException() throws ExecutionException, InterruptedException {
        // given
        ReflectionTestUtils.setField(companyBasicSearchService, "ENIRO_SERVICE_URL", ENIRO_SERVICE_URL);

        ResponseEntity<String> responseMock = mock(ResponseEntity.class);
        ListenableFuture<ResponseEntity<String>> featurMock = mock(ListenableFuture.class);
        when(featurMock.get()).thenThrow(ExecutionException.class);

        when(asyncRestTemplate.getForEntity(ENIRO_SERVICE_URL + KEY_WORDS.get(0), String.class)).thenReturn(featurMock);

        // when
        companyBasicSearchService.search(KEY_WORDS.get(0), null);

        // then
        verify(searchHistoryRepository, never()).save(anyCollection());
    }

}