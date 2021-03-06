/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jipijapa.eclipselink;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

import org.eclipse.persistence.platform.server.jboss.JBossPlatform;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.transaction.jboss.JBossTransactionController;

/**
 * The fully qualified name of JBossASServerPlatform must be set as the value
 * of the eclipselink.target-server property on EclipseLink version 2.3.2 and
 * older. In newer versions where bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=365704
 * has been fixed, setting eclipselink.target-server to "jboss" is sufficient.
 *
 * @author Craig Ringer <ringerc@ringerc.id.au>
 *
 */
public class JBossAS7ServerPlatform extends JBossPlatform {

    public JBossAS7ServerPlatform(DatabaseSession newDatabaseSession) {
        super(newDatabaseSession);
    }

    @Override
    public Class<?> getExternalTransactionControllerClass() {
        return JBossAS7TransactionController.class;
    }

    public static class JBossAS7TransactionController extends JBossTransactionController {
        @Override
        protected TransactionManager acquireTransactionManager() throws Exception {
            try {
                return InitialContext.doLookup("java:jboss/TransactionManager");
            } catch (NamingException ex) {
                return super.acquireTransactionManager();
            }
        }
    }

}
