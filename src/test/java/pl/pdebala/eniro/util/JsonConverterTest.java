package pl.pdebala.eniro.util;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pdebala.eniro.service.model.Company;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pdebala on 2016-10-11.
 */
@RunWith(SpringRunner.class)
public class JsonConverterTest {

    private static String JSON_CONTENT;

    private String START_NODE = "adverts";

    private JsonConverter jsonFilter = new JsonConverter();

    @BeforeClass
    public static void setUp() throws IOException {
        URL url = Resources.getResource("eniro-json-response.txt");
        JSON_CONTENT = Resources.toString(url, Charsets.UTF_8);
    }

   //@Test
    public void shouldFindFilteredDataAndReturnJsonObject() {
        // given
        final String filterCompanyName = "ADVOKATSKEPPET";

        // when
        Set<Company> result = jsonFilter.convert(JSON_CONTENT, START_NODE, Company.class, filterCompanyName);

        // then
        assertNotNull(result);
        assertTrue(!result.isEmpty());
        assertTrue(result.iterator().next().getCompanyInfo().getCompanyName().contains(filterCompanyName));
    }


    @Test
    public void shouldNotFindFilteredData() {
        // given
        final String filterCompanyName = "XXDVOKATSKEPPET";

        // when
        Set<Company> result = jsonFilter.convert(JSON_CONTENT, START_NODE, Company.class, filterCompanyName);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    @Test
    public void shouldFindDataAccordingToRegexpFilter() {
        // given
        final String filterCompanyName = "[A-Z]*";

        // when
        Set<Company> result = jsonFilter.convert(JSON_CONTENT, START_NODE, Company.class, filterCompanyName);

        // then
        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }

}