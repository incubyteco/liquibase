package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;

public class DropDefaultValueAction extends AbstractAction {
    public ObjectReference columnName;
    public String columnDataType;
}