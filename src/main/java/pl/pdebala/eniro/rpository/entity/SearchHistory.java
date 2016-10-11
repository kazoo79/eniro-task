package pl.pdebala.eniro.rpository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by pdebala on 2016-10-08.
 */
@Entity
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String keyWord;

    private String filter;

    protected SearchHistory() {
    }

    public SearchHistory(String keyWord, String filter) {
        this.keyWord = keyWord;
        this.filter = filter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", keyWord='" + keyWord + '\'' +
                ", filter='" + filter + '\'' +
                '}';
    }
}
