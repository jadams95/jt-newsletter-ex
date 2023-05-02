package com.jadams.jtnewsletterex.controller;

import com.jadams.jtnewsletterex.dao.ProductDao;
//import com.jadams.jtnewsletterex.dao.TemplateDao;
import com.jadams.jtnewsletterex.domain.*;
//import com.jadams.jtnewsletterex.service.TemplateService;
import com.jadams.jtnewsletterex.domain.model.ChatResponse;
import com.jadams.jtnewsletterex.service.TemplateService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private final ProductDao productDao;
    private final String TODO_API_URL = "https://api.openai.com/v1/completions";
    private final TemplateService templateService;

    @Autowired
    public ProductController(ProductDao productDao, TemplateService templateService){
        this.productDao = productDao;
        this.templateService = templateService;
    }

    @PostMapping("/uploadProductFileList")
    public String uploadProductExcelData(@RequestParam("file") MultipartFile file) throws Exception{
        List<Product> subscriberList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> allRecords = parser.parseAllRecords(inputStream);
        allRecords.forEach(record -> {
            Product product = new Product();
            product.setId(Long.parseLong(record.getString("id")));
            product.setProductName(record.getString("productName"));
            product.setProductCategory(record.getString("productCategory"));
            product.setProductType(record.getString("productType"));
            product.setProductDescription(record.getString("productDescription"));
            subscriberList.add(product);
        });
        productDao.saveAll(subscriberList);
        return "Upload Successful";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductLists(){
        List<Product> productList = productDao.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping(value = "/createTemplate")
    public ChatResponse getTemplateResponse(@RequestBody Message messageText) {
        return templateService.createAnContentPrompt(messageText);
    }
    @GetMapping(value = "/templates")
    public ResponseEntity<List<Template>> getTemplates(){
        return new ResponseEntity<>(templateService.getTemplates().stream().collect(Collectors.toList()), HttpStatus.OK);
    }

}
