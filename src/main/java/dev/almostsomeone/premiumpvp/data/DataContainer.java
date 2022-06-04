package dev.almostsomeone.premiumpvp.data;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DataContainer {

    private final UUID uuid;

    private Boolean tablesExist;
    protected List<DataGroup> dataGroups;

    public DataContainer() {
        uuid = UUID.randomUUID();
        init();
    }

    public DataContainer(final UUID uuid) {
        this.uuid = uuid;
        init();
    }

    private void init(){
        tablesExist = false;
        dataGroups = new ArrayList<>();
    }

    @Nonnull
    public abstract String tablePrefix();

    public UUID getUniqueId() {
        return uuid;
    }

    public void save() {
        if(dataGroups == null) return;
        for(DataGroup dataGroup : dataGroups)
            dataGroup.saveGroup();
    }

    public void createTables() {
        if(tablesExist) return;
        for(DataGroup dataGroup : dataGroups)
            if(dataGroup.inDatabase())
                dataGroup.createTable();
    }

    public void unload() {
        if(dataGroups == null) return;
        dataGroups.clear();
    }
}
