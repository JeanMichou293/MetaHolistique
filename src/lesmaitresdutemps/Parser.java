package lesmaitresdutemps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// #ONSEARETEICI : faire des arraylist à la place des tabs (separator = " " au lieu de "   ")

public class Parser
{
	public static final String SEPARATOR = " +";

	public static String readFile(File file)
	{
		String res = "";
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null)
				res += sCurrentLine + '\n';
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return res;
	}

	public static Project parse(String content)
	{
		Project project = new Project();

		try {
			// RECUPERATION DES LIGNES
			String[] lines = content.split("\n");

			// LIGNE D'INTRODUCTION AVEC RECUPERATION DES PARAMETRES
			String[] params = lines[0].split(SEPARATOR);
			int nb_jobs = Integer.parseInt(params[0]);
			int nb_machine = Integer.parseInt(params[1]);

			for (int i = 0; i < nb_jobs; i++) {
				project.addJob(new Job(i));
			}

			for (int i = 0; i < nb_machine; i++) {
				project.addMachine(new Machine(i + 1));
			}

			// ON VA MAINTENANT TRAITER CHAQUE LIGNE
			for (int i = 1; i < lines.length; i++) {
				int index = 0;
				Job job = project.getJob(i - 1);

				String[] line = lines[i].split(SEPARATOR);
				int nb_operations = Integer.parseInt(line[index++]);

				for (int j = 0; j < nb_operations; j++) {
					Operation operation = new Operation(j, job);

					job.addOperation(operation);
					int nb_machines = Integer.parseInt(line[index++]);

					for (int k = 0; k < nb_machines; k++) {
						operation.addMachine(
							project.getMachine(
								Integer.parseInt(line[index++]) - 1),
							Integer.parseInt(line[index++]));
						// System.out.println("Ajout de la machine "+line[index-2]+" avec temps de"+line[index-1]);
					}
				}
			}
		}

		catch (Exception e) {
			System.out.println("File is not readable!!!");
			e.printStackTrace();
		}

		return project;
	}
}
