package com.ethanaa.deepcrawl.writer.repository;


import com.ethanaa.deepcrawl.writer.entity.SearchResult;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SearchResultRepository extends PagingAndSortingRepository<SearchResult, String> {


}
