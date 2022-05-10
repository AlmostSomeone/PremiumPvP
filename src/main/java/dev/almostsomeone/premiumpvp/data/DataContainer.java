package dev.almostsomeone.premiumpvp.data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DataContainer {

    private final UUID uuid;

    private Boolean tablesExist;
    protected List<DataGroup> dataGroups;

    public DataContainer() {
        this.uuid = UUID.randomUUID();
        this.init();
    }

    public DataContainer(final UUID uuid) {
        this.uuid = uuid;
        this.init();
    }

    private void init(){
        this.tablesExist = false;
        this.dataGroups = new ArrayList<>();
    }

    @NotNull
    public abstract String tablePrefix();

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void save() {
        if(this.dataGroups == null) return;
        for(DataGroup dataGroup : this.dataGroups)
            dataGroup.saveGroup();
    }

    public void createTables() {
        if(tablesExist) return;
        for(DataGroup dataGroup : this.dataGroups)
            if(dataGroup.inDatabase())
                dataGroup.createTable();
    }

    public void unload() {
        if(this.dataGroups == null) return;
        this.dataGroups.clear();
    }
}
