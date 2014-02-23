package com.rikardlegge.mediarenderer;

/*
 * Copyright (C) the Pi-mediacaster contributors. All rights reserved.
 * This file is part of Pi-mediacaster, distributed under the GNU GPL v2 with
 * a Linking Exception. For full terms see the included COPYING file.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

class ShellCmd {
	Process p;
	Runtime r;
	BufferedReader reader;
	BufferedWriter writer;
	Thread thread;

	ArrayList<String> stack;

	ShellCmd() {
		// See function create(String command) for case used.
		r = Runtime.getRuntime();
	}

	public void startProcess(final String command, String args) {
		System.out.println("$ " + command);

		// This is a previous version whih used the getRuntime instead of the
		// processbuilder. Might work
		/*
		 * try {
		 * p = r.exec(command);
		 * reader = new BufferedReader(new
		 * InputStreamReader(p.getInputStream()));
		 * writer = new BufferedWriter(new
		 * OutputStreamWriter(p.getOutputStream()));
		 * String line = "";
		 * while ((line = reader.readLine()) != null) {
		 * System.out.println(line);
		 * }
		 * } catch (Exception e) {
		 * e.printStackTrace();
		 * }
		 */

		try {

			ProcessBuilder builder = new ProcessBuilder("bash", "-c", command, args);
			p = builder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeCommand(String command) {
		System.out.println("$ " + command);
		try {
			// uses the getRuntime instead of projectbuilder, since i was unable
			// to get the processbuilder to execute long lines of shell code
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}