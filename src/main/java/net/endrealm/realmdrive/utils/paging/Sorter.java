package net.endrealm.realmdrive.utils.paging;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class Sorter {
    private final String key;
    private final SortingOrder order;
}
