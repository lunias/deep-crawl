package com.ethanaa.deepcrawl.writer.repository;


import com.ethanaa.deepcrawl.writer.entity.ScrapedPage;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ScrapedPageRepository extends PagingAndSortingRepository<ScrapedPage, String> {

    @Override
    @EnableScan
    @EnableScanCount
    Page<ScrapedPage> findAll(Pageable pageable);
}
