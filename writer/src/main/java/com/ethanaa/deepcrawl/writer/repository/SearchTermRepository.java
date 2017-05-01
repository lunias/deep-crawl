package com.ethanaa.deepcrawl.writer.repository;


import com.ethanaa.deepcrawl.writer.entity.SearchTerm;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SearchTermRepository extends PagingAndSortingRepository<SearchTerm, String> {


}
