package com.vero.admin;

import java.util.UUID;

public class DeleteTeradata extends DataSource {        
    public DeleteTeradata(UUID uuid, DsType inType, String inName, String inDescription) {
        super(uuid, inType, inName, inDescription);
    }
}
