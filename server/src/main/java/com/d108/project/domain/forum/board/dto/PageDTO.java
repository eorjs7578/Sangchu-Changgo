package com.d108.project.domain.forum.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

@Setter
@Getter
public class PageDTO<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PageDTO() {}

    public PageDTO(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
