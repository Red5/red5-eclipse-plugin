/******************************************************************************
 * Copyright (c) 2006 BEA Systems, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package org.red5.eclipse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.osgi.framework.Bundle;

/**
 * @author daccattato
 *
 */
public final class Utils
{
    /**
     * Returns the location of the web project's WEB-INF/lib directory.
     *
     * @param pj the web project
     * @return the location of the WEB-INF/lib directory
     */

    public static IFolder getWebInfLibDir( final IProject pj )
    {
        final IVirtualComponent vc = ComponentCore.createComponent( pj );
        final IVirtualFolder vf = vc.getRootFolder().getFolder( "WEB-INF/lib" );
        return (IFolder) vf.getUnderlyingFolder();
    }
    
    /**
     * @param pj
     * @return
     */
    public static IFolder getWebInfDir( final IProject pj )
    {
        final IVirtualComponent vc = ComponentCore.createComponent( pj );
        final IVirtualFolder vf = vc.getRootFolder().getFolder( "WEB-INF" );
        return (IFolder) vf.getUnderlyingFolder();
    }
    
    /**
     * @param pj
     * @return
     */
    public static IFolder getWebAppDir( final IProject pj )
    {
        //final IVirtualComponent vc = ComponentCore.createComponent( pj );
        return (IFolder) pj.getFolder("src/org/red5/core");
//        final IVirtualFolder vf = vc.getRootFolder().getFolder( "src/org/red5/core/" );
//        return (IFolder) vf.getUnderlyingFolder();
    }

    /**
     * Copies a resource from within the FormGen plugin to a destination in
     * the workspace.
     *
     * @param src the path of the resource within the plugin
     * @param dest the destination path within the workspace
     */

    public static void copyFromPlugin( final IPath src,
                                       final IFile dest )

        throws CoreException

    {
        try
        {
        	final Bundle bundle = Red5Plugin.getInstance().getBundle();
            final InputStream in = FileLocator.openStream( bundle, src, false );
            
            if(dest.exists()) {
            	dest.delete(true, null);
            }
            dest.create( in, true, null );
        
        }
        catch( IOException e )
        {
            throw new CoreException( Red5Plugin.createErrorStatus( e.getMessage(), e ) );
        }
    }
    
    /**
     * @param folder
     */
    public static void setup(IFolder folder) {
		// TODO Auto-generated method stub
		IProject prj = folder.getProject();
		IFolder srcOrg = prj.getFolder(new Path("src/org"));
		IFolder srcRed5 = prj.getFolder(new Path("src/org/red5"));
		IFolder srcRed5Core = prj.getFolder(new Path("src/org/red5/core"));
		
		try {
			srcOrg.create(true, true, null);
			srcRed5.create(true, true, null);
			srcRed5Core.create(true, true, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    /**
     * @return
     */
    public static File getPluginFolderRoot() {
    	File _pluginFolder = null;
        if(_pluginFolder == null) {
            URL url = Platform.getBundle("org.red5.wizard").getEntry("/");
            try {
                url = Platform.resolve(url);
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
            _pluginFolder = new File(url.getPath());
        }

        return _pluginFolder;
 }
    
    /**
     * @return
     */
    public static File getPluginFolder() {
        Object _pluginFolder = null;
		if(_pluginFolder == null) {
			final Bundle bundle = Red5Plugin.getInstance().getBundle();
            URL urlFile = FileLocator.find(bundle, new Path("build_red5.xml"), null);
            URL urlFile3 = null;
            URL entry = bundle.getEntry("build_red5.xml");
			try {
				urlFile3 = FileLocator.resolve(entry);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            
            try {
				urlFile3 = Platform.resolve(urlFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            IPath path1 = Platform.getLocation();
            path1.append(new Path("build_red5.xml"));
            _pluginFolder = new File(urlFile3.getPath());
        }

        return (File) _pluginFolder;
    }

    /**
     * Adds entries in the web.xml file for the FormGen servlet. Uses the
     * "*.form" for the URL pattern.
     *
     * @param pj the web application that the servlet is part of
     */

    public static void registerFormGenServlet( final IProject pj )
    {
        //registerFormGenServlet( pj, "*.form" );
    }
    /**
     * Adds entries in the web.xml file for the FormGen servlet.
     *
     * @param pj the web application that the servlet is part of
     * @param urlPattern the url pattern for the servlet
     */

    public static void registerFormGenServlet( final IProject pj,
                                               final String urlPattern )
    {
//        final WebArtifactEdit artifact
//            = WebArtifactEdit.getWebArtifactEditForWrite( pj );
//
//        try
//        {
//	        final WebApp root = artifact.getWebApp();
//	
//	        final Servlet servlet = WebapplicationFactory.eINSTANCE.createServlet();
//	        final ServletType servletType = WebapplicationFactory.eINSTANCE.createServletType();
//	        servletType.setClassName( "com.formgen.core.FormGenServlet" );
//	        servlet.setWebType( servletType );
//	        servlet.setServletName( "FormGenServlet" );
//	        root.getServlets().add( servlet );
//	
//	        final ServletMapping mapping
//	            = WebapplicationFactory.eINSTANCE.createServletMapping();
//	
//	        mapping.setServlet( servlet );
//	        mapping.setUrlPattern( urlPattern );
//	        root.getServletMappings().add( mapping );
//
//	        artifact.saveIfNecessary( null );
//        }
//        finally
//        {
//        	artifact.dispose();
//        }
    }

	/**
	 * @param file
	 */
	public static void removeFile(IFile file) {
		// TODO Auto-generated method stub
		try {
			file.delete(true, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
