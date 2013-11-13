package org.red5.eclipse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * @author daccattato
 *
 */
public final class Red5CoreFacetInstallDelegate implements IDelegate
{
    /* (non-Javadoc)
     * @see org.eclipse.wst.common.project.facet.core.IDelegate#execute(org.eclipse.core.resources.IProject, org.eclipse.wst.common.project.facet.core.IProjectFacetVersion, java.lang.Object, org.eclipse.core.runtime.IProgressMonitor)
     */
    public void execute( final IProject pj,
                         final IProjectFacetVersion fv,
                         final Object config,
                         final IProgressMonitor monitor )

        throws CoreException

    {
        monitor.beginTask( "", 2 );

        File myFile = Utils.getPluginFolder();
        
        Project p = new Project();
		String tmpStr = pj.getName();
		
		
		//run ant to replace template variables
		IWorkspaceRoot root = runAnt(pj, myFile, p, tmpStr);
		
		//create project directory structure
		IProject proj1 = createProjectStructure(pj, root);
		
		
		Utils.copyFromPlugin( new Path( "tmp/Flex/.actionScriptProperties"), proj1.getFile(".actionScriptProperties"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/.flexProperties"), proj1.getFile(".flexProperties"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/.project"), proj1.getFile(".project"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/src/Test.mxml"), proj1.getFile("src/" + proj1.getName() + ".mxml"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/html-template/AC_OETags.js"), proj1.getFile("html-template/AC_OETags.js"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/html-template/index.template.html"), proj1.getFile("html-template/index.template.html"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/html-template/playerProductInstall.swf"), proj1.getFile("html-template/playerProductInstall.swf"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/html-template/history/history.css"), proj1.getFile("html-template/history/history.css"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/html-template/history/history.js"), proj1.getFile("html-template/history/history.js"));
		Utils.copyFromPlugin( new Path( "tmp/Flex/html-template/history/historyFrame.html"), proj1.getFile("html-template/history/historyFrame.html"));
		proj1.refreshLocal(0, null);
		
        final Red5CoreFacetInstallConfig cfg = (Red5CoreFacetInstallConfig) config;
        final IFolder webInf = Utils.getWebInfDir( pj );
       
        Utils.copyFromPlugin( new Path( "tmp/Red5/WEB-INF/red5-web.properties" ), webInf.getFile( "red5-web.properties" ) );
        Utils.copyFromPlugin( new Path( "tmp/Red5/WEB-INF/red5-web.xml" ), webInf.getFile( "red5-web.xml" ) );
        Utils.removeFile( webInf.getFile("web.xml"));            
        Utils.copyFromPlugin( new Path( "tmp/Red5/WEB-INF/web.xml" ), webInf.getFile( "web.xml" ) );
        Utils.setup(webInf);
        
        final IFolder webAppDir = Utils.getWebAppDir(pj);
        
        Utils.copyFromPlugin( new Path( "tmp/Red5/WEB-INF/src/org/red5/core/SimpleApplication.java" ),
                webAppDir.getFile( "Application.java" ) );

        // get user home directory, add trust
        String userHome = System.getProperty("user.home");
        StringBuffer userHomeBuf = new StringBuffer();
        userHomeBuf.append(userHome);
        String osName = System.getProperty("os.name");
        if(osName.indexOf("Mac") != -1) {
        	System.out.println("were on a mac");
        	userHomeBuf.append("/Library/Preferences/Macromedia/Flash Player/#Security/FlashPlayerTrust/");
        } else {
        	System.out.println("were on a windows");
        	userHomeBuf.append("/Application Data/Macromedia/Flash Player/#Security/FlashPlayerTrust/");
        }
       //userHome += 
        String fileName = userHomeBuf.append("R5trusted.cfg").toString();
        File file = new File(fileName);
        
        FileWriter fstream = null;
		BufferedWriter out = null;
        
        if(file.exists()) {
        	System.out.println("file exists");
    	
			try {
				fstream = new FileWriter(file);
				out = new BufferedWriter(fstream);
                
                String projectLocation = ResourcesPlugin.getWorkspace().getRoot().getProject(pj.getName()).getLocation().toString();
				String debugFolder = projectLocation + "Client/bin-debug/";
				//String releaseFolder = projectLocation + "Client/bin";
				String fileFolder = "file://localhost" + projectLocation + "Client/bin-debug/";
                
                out.newLine();
                out.append(debugFolder);
                out.newLine();
                out.append(fileFolder);
                out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } else {
        	System.out.println("file doesn't exists");
        	try {
        		
				if(file.createNewFile()) {
					fstream = new FileWriter(file);
                    out = new BufferedWriter(fstream);
					
					String projectLocation = ResourcesPlugin.getWorkspace().getRoot().getProject(pj.getName()).getLocation().toString();
					String debugFolder = projectLocation + "Client/bin-debug/";
					//String releaseFolder = projectLocation + "Client/bin";
					String fileFolder = "file://localhost" + projectLocation + "Client/bin-debug/";
					
					out.write(debugFolder);
					out.newLine();
					out.write(fileFolder);
					
					out.close();
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
            
            
            monitor.worked( 1 );
            Utils.registerFormGenServlet( pj, cfg.getUrlPattern() );
            monitor.worked( 1 );
            monitor.done();
   
    }

	/**
	 * @param pj
	 * @param root
	 * @return
	 * @throws CoreException
	 */
	private IProject createProjectStructure(final IProject pj,
			IWorkspaceRoot root) throws CoreException {
		IProject proj1 = root.getProject(pj.getName() + "Client");
		if(!proj1.exists()) {
			proj1.create(null);
			proj1.open(null);
		}
		
		IFolder tempFolder = proj1.getFolder("src");
		IFolder tempFolder1 = proj1.getFolder("libs");
		IFolder tempFolder2 = proj1.getFolder("html-template");
		IFolder tempFolder5 = tempFolder2.getFolder("history");
		IFolder tempFolder3 = proj1.getFolder("bin-debug");
		IFolder tempFolder4 = proj1.getFolder(".settings");
		
		tempFolder.create(true, true, null);
		tempFolder1.create(true, true, null);
		tempFolder2.create(true, true, null);
		tempFolder3.create(true, true, null);
		tempFolder4.create(true, true, null);
		tempFolder5.create(true, true, null);
		return proj1;
	}

	/**
	 * @param pj
	 * @param myFile
	 * @param p
	 * @param tmpStr
	 * @return
	 */
	private IWorkspaceRoot runAnt(final IProject pj, File myFile, Project p,
			String tmpStr) {
		p.setUserProperty("ant.file", myFile.getAbsolutePath());
		
		IWorkspace space = pj.getWorkspace();
		IWorkspaceRoot root = space.getRoot();
		
		p.setProperty("APP_NAME", tmpStr);
		p.init();
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		p.addReference("ant.projectHelper", helper);
 		helper.parse(p, myFile);
 		DefaultLogger consoleLogger = new DefaultLogger();
 		consoleLogger.setErrorPrintStream(System.err);
 		consoleLogger.setOutputPrintStream(System.out);
 		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
 		p.addBuildListener(consoleLogger);
 		p.executeTarget("copy");
 		p.executeTarget(p.getDefaultTarget());
		return root;
	}
}
