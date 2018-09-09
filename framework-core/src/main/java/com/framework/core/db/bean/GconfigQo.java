package com.framework.core.db.bean;

import java.io.Serializable;


public class GconfigQo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String groupName;
    private String configName;
    private String portalKey;
    
    public GconfigQo(String groupName, String configName, String portalKey ) {
        super();
        this.groupName = groupName;
        this.configName = configName;
        this.portalKey = portalKey;
    }
    public GconfigQo(String groupName, String configName ) {
        super();
        this.groupName = groupName;
        this.configName = configName;
    }
    public GconfigQo(  ) {
    }
    
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName( String groupName ) {
        this.groupName = groupName;
    }
    public String getConfigName() {
        return configName;
    }
    public void setConfigName( String configName ) {
        this.configName = configName;
    }
    public String getPortalKey() {
        return portalKey;
    }
    public void setPortalKey( String portalKey ) {
        this.portalKey = portalKey;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((configName == null) ? 0 : configName.hashCode());
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
        return result;
    }
    @Override
    public boolean equals( Object obj ) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GconfigQo other = (GconfigQo) obj;
        if (configName == null) {
            if (other.configName != null)
                return false;
        } else if (!configName.equals( other.configName ))
            return false;
        if (groupName == null) {
            if (other.groupName != null)
                return false;
        } else if (!groupName.equals( other.groupName ))
            return false;
        return true;
    }
}
