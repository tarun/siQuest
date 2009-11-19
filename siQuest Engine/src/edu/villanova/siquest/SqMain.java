package edu.villanova.siquest;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SqMain {

	
	static Set<File> sourceFiles;
	static SqPreferences prefs;	
	static SqSearchAPI searchAPI = null;

	
	private static boolean parseArguments(String[] args)
	{
		// parse all options
		int i = 0;
		for ( ; i < args.length; i++)
		{
			String arg = args[i];
			
			// TODO: add options
			if ("-h".equals(arg) || "-help".equals(arg) || "--help".equals(arg))
			{
				return false;
			}
			else if ("-a".equals(arg) || "-api".equals(arg) || "--api".equals(arg))
			{
				i++;
				if (i >= args.length)
					return false;
				
				searchAPI = SqSearchAPI.getAPI(args[i]);
				if (searchAPI == null)
					return false;
			}
			else
			{
				break;
			}
		}
		
		// no files specified
		if (i >= args.length)
			return false;
		
		// get source files
		sourceFiles = new HashSet<File>(args.length - i);
		for ( ; i < args.length; i++)
		{
			File file = new File(args[i]);
			if (!file.exists())
			{
				System.err.println("WARNING: " + args[i] + " does not exist or is not a valid file/directory");
				continue;
			}
			sourceFiles.add(file);
		}
		
		return (sourceFiles != null && sourceFiles.size() > 0);
	}
	
	private static void usage()
	{
		System.err.println("USAGE: java SqMain [ OPTIONS ] <file> [ <file2> ... ]");
		System.err.println("OPTIONS:");
		System.err.println("    -h | --help         This help menu");
		System.err.println("    -a | --api  name    Use the search API specified by 'name'");
		System.err.print  ("                        name can be one of ");
		SqSearchAPI[] apis = SqSearchAPI.values();
		int i = 0;
		for ( ; i < apis.length - 1; i++)
		{
			System.err.print("'" + apis[i].getName() + "', ");
		}
		System.err.println("'" + apis[i].getName() + "'");
		System.err.println("                        Default API is '" + prefs.getSearchAPI() + "'");
		System.err.println();		
	}
	
		
	public static void main(String[] args)
	{
		// Set application preferences
		prefs = SqPreferences.getInstance();
				
		if (!parseArguments(args))
		{
			usage();
			System.exit(1);
		}
		
		// Create query builder instance
		SqQueryBuilder builder = new SqQueryBuilder();
		
		// Set miscellaneous options
		builder.setAnalyzer(prefs.getAnalyzerName());
		builder.setIndexDirectory(prefs.getIndexDir());
		builder.setOverwriteIndex(true);

		if (!builder.prepareIndex())
		{
			System.exit(1);
		}
		
		
		// add the source files and index them
		builder.indexSources(sourceFiles);
		builder.optimizeIndex();
		
		
		// Build the query string and show result
		if (searchAPI == null)
			searchAPI = SqSearchAPI.getAPI(prefs.getSearchAPI());			
		SqQuery query = builder.getQuery(searchAPI);
		System.out.println("Generated " + searchAPI.getName() + " Query:");
		System.out.println(query);
		System.out.println();
		
		// Uncomment below for debugging
		System.out.println("Detailed query term information:");
		int i = 1;
		for (SqTerm term : query.getTermList())
		{
			System.out.println(i + ".\n" + term);
			i++;
		}
	}
}
