package tpi.dlc.tpidlcmotorbusquedaficheros.api.controller;


import tpi.dlc.tpidlcmotorbusquedaficheros.api.dto.DocumentResult;
import tpi.dlc.tpidlcmotorbusquedaficheros.searchEngine.SearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/search")
public class SearchRestController {

    @Autowired
    private SearchEngine searchEngine;


    @GetMapping
    public List<DocumentResult> search(@RequestParam("query") String query,
                                       @RequestParam(value = "r", required = false)Integer r){
        if(r == null) r = 10;
        List<DocumentResult> documentResultList = searchEngine.search(query, r);
        return documentResultList;
    }

}
