package pl.pdebala.eniro.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pdebala.eniro.service.CompanyBasicSearch;
import pl.pdebala.eniro.service.CompanyBasicSearchService;
import pl.pdebala.eniro.web.model.SearchCriteria;

import javax.validation.Valid;

/**
 * Created by pdebala on 2016-10-08.
 */
@Controller
public class SearchController {

    @Autowired
    private CompanyBasicSearch companyBasicSearchService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Model model, @Valid SearchCriteria criteria, BindingResult result) {
        if (!result.hasErrors()) {
            System.out.println("SearchController - before search: ");
            model.addAttribute("deferredResults", companyBasicSearchService.search(criteria.getCommaSeparatedKeys(), criteria.getFilter()));
            System.out.println("SearchController - after search: ");
            model.addAttribute("history", companyBasicSearchService.getHistory());
        }
        System.out.println("SearchController - return: ");
        return "search";
    }

}
