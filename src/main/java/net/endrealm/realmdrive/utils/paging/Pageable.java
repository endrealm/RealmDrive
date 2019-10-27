package net.endrealm.realmdrive.utils.paging;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Pageable {
    private final int page, perPage;
    private final List<Sorter> sorters;

    /**
     * Creates a new Pageable Builder
     *
     * @param page the current page. If less than 0, it will be set to 0.
     * @param pageSize how many entries are on one page. If less than one, it will be set to 1.
     * @return a new Pageable builder
     */
    public static PageableBuilder builder(int page, int pageSize) {
        if(page < 0)
            page = 0;
        if(pageSize < 1)
            pageSize = 1;

        return new PageableBuilder(page, pageSize, new ArrayList<>());
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class PageableBuilder {
        private int page, perPage;
        private final List<Sorter> sorters;

        /**
         * Add a new sorting object. Added and commonly used according to FIFO.
         *
         * @param key what key to use for searching
         * @param order in what direction should this be sorted
         */
        public void addSorting(String key, SortingOrder order) {
            sorters.add(new Sorter(key, order));
        }

        /**
         * Sets the current page. This value is already set on builder
         * creation. If less than zero it will be set to zero.
         *
         * @param page current page
         */
        public void setPage(int page) {
            if(page < 0)
                return;
            this.page = page;
        }

        /**
         * Sets the amount of entries per page. This value is already
         * set on builder creation. If less than one it will be set
         * to one.
         *
         * @param perPage entries per page
         */
        public void setPerPage(int perPage) {
            if(perPage < 1)
                return;
            this.perPage = perPage;
        }

        /**
         * Uses the data in the builder to construct a new Pageable
         *
         * @return new pageable
         */
        public Pageable build() {
            return new Pageable(page, perPage, sorters);
        }
    }
}
