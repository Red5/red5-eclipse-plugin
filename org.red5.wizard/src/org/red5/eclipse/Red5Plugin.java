/******************************************************************************
 * Copyright (c) 2006 BEA Systems, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package org.red5.eclipse;

import org.eclipse.ui.plugin.*;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * @author daccattato
 *
 */
public final class Red5Plugin

    extends AbstractUIPlugin

{
    public static final String PLUGIN_ID = "org.red5.eclipse";

    private static Red5Plugin plugin;

    /**
     * 
     */
    public Red5Plugin()
    {
        plugin = this;
    }

    /**
     * @return
     */
    public static Red5Plugin getInstance()
    {
        return plugin;
    }

    /**
     * @param e
     */
    public static void log( final Exception e )
    {
        final String msg = e.getMessage() + "";
        log( new Status( IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, e ) );
    }

    /**
     * @param status
     */
    public static void log( final IStatus status )
    {
        getInstance().getLog().log( status );
    }

    /**
     * @param msg
     */
    public static void log( final String msg )
    {
        log( new Status( IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, null ) );
    }

    /**
     * @param msg
     * @return
     */
    public static IStatus createErrorStatus( final String msg )
    {
        return createErrorStatus( msg, null );
    }

    /**
     * @param msg
     * @param e
     * @return
     */
    public static IStatus createErrorStatus( final String msg,
                                             final Exception e )
    {
        return new Status( IStatus.ERROR, PLUGIN_ID, 0, msg, e );
    }

}
