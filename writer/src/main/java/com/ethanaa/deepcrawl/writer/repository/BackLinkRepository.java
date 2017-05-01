package com.ethanaa.deepcrawl.writer.repository;


import com.ethanaa.deepcrawl.writer.entity.BackLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface BackLinkRepository extends PagingAndSortingRepository<BackLink, String> {

    Page<BackLink> findByFromUriAuthority(@Param("fromUriAuthority") String fromUriAuthority, Pageable pageable);

}
