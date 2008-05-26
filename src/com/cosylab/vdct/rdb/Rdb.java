/**
 * Copyright (c) 2008, Cosylab, Ltd., Control System Laboratory, www.cosylab.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution. 
 * Neither the name of the Cosylab, Ltd., Control System Laboratory nor the names
 * of its contributors may be used to endorse or promote products derived 
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.cosylab.vdct.rdb;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.cosylab.vdct.db.DBData;
import com.cosylab.vdct.rdb.group.SQLTableGUI;

/**
 * @author ssah
 *
 */
public class Rdb implements RdbInterface {

	private DataMapper mapper = null;
	private ConnectionDialog connectionDialog = null;
	private SQLTableGUI groupDialog = null;
	private JFrame guiContext = null;

	/**
	 * 
	 */
	public Rdb(JFrame guiContext) {
		super();
		this.guiContext = guiContext; 
		try {
		    mapper = new DataMapper();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		connectionDialog = new ConnectionDialog(guiContext, mapper);
		groupDialog = new SQLTableGUI(mapper, guiContext);
	}

	/* (non-Javadoc)
	 * @see com.cosylab.vdct.rdb.RdbInterface#connect(java.awt.Frame)
	 */
	public void connect() {
		connectionDialog.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see com.cosylab.vdct.rdb.RdbInterface#loadDbGroup(java.lang.String)
	 */
	public DBData loadDbGroup() {
		if (!mapper.isConnection()) {
			connectionDialog.setVisible(true);
		}
		if (mapper.isConnection()) {
			groupDialog.setLoadMode(true);		
			groupDialog.setVisible(true);
			return groupDialog.getData();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cosylab.vdct.rdb.RdbInterface#saveDbGroup(java.lang.String)
	 */
	public void saveDbGroup(String name) {
		if (!mapper.isConnection()) {
			connectionDialog.setVisible(true);
		}
		if (mapper.isConnection()) {

			try {
				mapper.saveDbGroup(name);
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(guiContext, exception.getMessage(),
						"Database error", JOptionPane.ERROR_MESSAGE);

				groupDialog.setLoadMode(false);		
				groupDialog.setVisible(true);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cosylab.vdct.rdb.RdbInterface#saveAsDbGroup(java.lang.String)
	 */
	public void saveAsDbGroup(String name) {
		if (!mapper.isConnection()) {
			connectionDialog.setVisible(true);
		}
		if (mapper.isConnection()) {
			groupDialog.setLoadMode(false);		
			groupDialog.setVisible(true);
		}
	}
}