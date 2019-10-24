package net.endrealm.realmdrive.utils.paging;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Pageable {
    private final int page, perPage;
    private final List<Sorter> sorters;

    public static PageableBuilder builder(int page, int pageSize) {
        return new PageableBuilder(page, pageSize, new ArrayList<>());
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class PageableBuilder {
        private int page, perPage;
        private final List<Sorter> sorters;

        public void addSorting(String key, SortingOrder order) {
            sorters.add(new Sorter(key, order));
        }

        public void setPage(int page) {
            if(page < 0)
                return;
            this.page = page;
        }

        public void setPerPage(int perPage) {
            if(perPage < 1)
                return;
            this.perPage = perPage;
        }

        public Pageable build() {
            return new Pageable(page, perPage, sorters);
        }
    }
}
