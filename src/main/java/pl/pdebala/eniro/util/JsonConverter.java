package pl.pdebala.eniro.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by pdebala on 2016-10-11.
 */
@Component
public class JsonConverter {

    private final Logger logger = LoggerFactory.getLogger(JsonConverter.class.getName());

    public <T> Set<T> convert(final String json, final String startNode, Class<T> targetClazz, final String filter) {
        if (StringUtils.isEmpty(json)) {
            return new HashSet<>();
        }
        ObjectMapper mapper = new ObjectMapper();
        Map obj = null;
        try {
            obj = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            logger.error("Exception while trying to deserialize JSON content." + e);
        }

        Pattern pattern = Pattern.compile(filter);
        List<Map> nodesMap = (List<Map>) obj.get(startNode);

        return nodesMap == null ? new HashSet<>() : nodesMap.stream()
                .filter(node -> doFilter(node, pattern))
                .map(node -> mapper.convertValue(node, targetClazz))
                .collect(Collectors.<T>toSet());
    }


    private boolean doFilter(Map root, Pattern pattern) {
        return StringUtils.isEmpty(pattern) || root.values().stream().anyMatch(o -> {
            if (o != null) {
                if (o instanceof LinkedHashMap) {
                    if (doFilter((LinkedHashMap) o, pattern)) {
                        return true;
                    }
                } else if (o instanceof String) {
                    System.out.println(o);
                    if (pattern.matcher((String) o).matches()) {
                        System.out.println("CONTAINS");
                        return true;
                    }
                }
            }
            return false;
        });
    }
}
