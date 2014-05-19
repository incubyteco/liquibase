package liquibase.integration.ant;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.util.ui.UIFactory;
import org.apache.tools.ant.BuildException;

/**
 * Ant task for migrating a database forward testing rollback.
 */
public class DatabaseUpdateTestingRollbackTask extends AbstractChangeLogBasedTask {
    private boolean dropFirst = false;

    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Liquibase liquibase = getLiquibase();
        try {
            if (isPromptOnNonLocalDatabase()
                    && !liquibase.isSafeToRunUpdate()
                    && UIFactory.getInstance().getFacade().promptForNonLocalDatabase(liquibase.getDatabase())) {
                throw new BuildException("Chose not to run against non-production database");
            }

            if (isDropFirst()) {
                liquibase.dropAll();
            }

            liquibase.updateTestingRollback(getContexts());
        } catch (LiquibaseException e) {
            throw new BuildException("Unable to update database with a rollback test.", e);
        }
    }

    public boolean isDropFirst() {
        return dropFirst;
    }

    public void setDropFirst(boolean dropFirst) {
        this.dropFirst = dropFirst;
    }
}