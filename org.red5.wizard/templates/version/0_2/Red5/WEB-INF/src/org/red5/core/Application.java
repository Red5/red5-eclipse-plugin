package org.red5.core;

/*
 * RED5 Open Source Flash Server - http://www.osflash.org/red5
 * 
 * Copyright (c) 2006-2007 by respective authors (see below). All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License as published by the Free Software 
 * Foundation; either version 2.1 of the License, or (at your option) any later 
 * version. 
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along 
 * with this library; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 */

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.service.ServiceUtils;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.red5.server.framework.ClientManager;
import org.red5.server.framework.LayoutManager;
import org.red5.server.framework.StreamManager;
import org.red5.server.framework.*;

/**
 * Red5Server Framework.
 * 
 * @author The Red5 Project (red5@osflash.org)
 * @author Dominick Accattato
 * @author Joachim Bauch (jojo@struktur.de)
 */
public class Application extends ApplicationAdapter implements IStreamAwareScopeHandler {
	
	/** Manager for the clients. */
	private ClientManager clientMgr = new ClientManager("clientlist", false);
	private StreamManager streamMgr = new StreamManager("streamlist", false);
	private LayoutManager layoutMgr = new LayoutManager("layoutlist", false);
	
	public Application() {
		System.out.println("test");
	}
	
	/** {@inheritDoc} */
    @Override
	public boolean connect(IConnection conn, IScope scope, Object[] params) {
		// Check if the user passed valid parameters.
		if (params == null || params.length == 0) {
			// NOTE: "rejectClient" terminates the execution of the current method!
			rejectClient("No username passed.");
		}

		// Call original method of parent class.
		if (!super.connect(conn, scope, params)) {
			return false;
		}

		String username = params[0].toString();
		String uid = conn.getClient().getId();
		
		Client client = new Client();
		client.setUsername(username);
		client.setAge(100);
		client.setUid(uid);
		
		// Register the user in the shared object.
		clientMgr.addClient(scope, client);
		
		// Notify client about unique id.
		ServiceUtils.invokeOnConnection(conn, "setClientID",
				new Object[] { uid });
		return true;
	}

	/** {@inheritDoc} */
    @Override
	public void disconnect(IConnection conn, IScope scope) {
		// Get the previously stored username.
		String uid = conn.getClient().getId();
		// Unregister user.
		String username = clientMgr.removeClient(scope, uid);
		
		// Call original method of parent class.
		super.disconnect(conn, scope);
	}

    @Override
	public void streamBroadcastClose(IBroadcastStream stream) {
		// TODO Auto-generated method stub
		super.streamBroadcastClose(stream);
		
		// Get connection and Scope
		IConnection conn = Red5.getConnectionLocal();
	    IScope scope = conn.getScope();
		
		streamMgr.removeStream(scope, stream.getName());
		
	}

	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		// TODO Auto-generated method stub
		super.streamBroadcastStart(stream);
		
		// Get connection and Scope
		IConnection conn = Red5.getConnectionLocal();
	    IScope scope = conn.getScope();
	    
	    streamMgr.addStream(scope, stream.getPublishedName(), stream.getName());
	    
	}

	@Override
	public void streamPublishStart(IBroadcastStream stream) {
		// TODO Auto-generated method stub
		super.streamPublishStart(stream);
		
	}
	
}
