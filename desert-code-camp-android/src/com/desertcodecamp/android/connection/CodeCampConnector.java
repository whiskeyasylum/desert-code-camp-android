package com.desertcodecamp.android.connection;

import com.desertcodecamp.android.models.Session;

public class CodeCampConnector {

	private ConnectorCallback callback;

	public CodeCampConnector(ConnectorCallback callback) {
		this.callback = callback;
	}

	public void requestSessions() {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Session session = Session.getAll();
				Session session = new Session();
				callback.handleConnectorCallback(0, session);
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}

}
