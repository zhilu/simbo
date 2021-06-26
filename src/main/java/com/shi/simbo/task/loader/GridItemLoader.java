package com.shi.simbo.task.loader;

import com.shi.simbo.entity.GridItem;

import java.util.List;

public interface GridItemLoader {
    default void setContext(LoaderConfig context){ };
    List<GridItem> loadItems();
}
