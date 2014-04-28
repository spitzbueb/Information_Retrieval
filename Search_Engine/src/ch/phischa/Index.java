package ch.phischa;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Index {
	
	public IndexWriter writer;
	long dauer;
	
	public Index(String pfad)
	{
		String pfadIndex = pfad + File.separator + "Index";
		int numIndexed = 0;
		try {
			Indexer(pfadIndex);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		long start = System.currentTimeMillis();

		try{
			numIndexed = index(pfad, new MPFileFilter());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Indexing " + numIndexed + " in " + (end - start) + " Milliseconds");
		dauer = end - start;
	}
	
	@SuppressWarnings("deprecation")
	public void Indexer(String indexDir) throws IOException
	{
		Directory dir = FSDirectory.open(new File(indexDir));
		writer = new IndexWriter(dir,new StandardAnalyzer(Version.LUCENE_30),true,IndexWriter.MaxFieldLength.UNLIMITED);
	}
	
	public void close()
	{
		try {
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int index(String dataDir, FileFilter filter) throws Exception
	{
		File[] files = new File(dataDir).listFiles();
		
		for(File f:files)
		{
			if(!f.isDirectory() && !f.isHidden() && f.canRead() && f.exists() && (filter == null || filter.accept(f)))
			{
				indexFile(f);
			}
		}
		
		return writer.numDocs();
	}
	
	private static class MPFileFilter implements FileFilter
	{
		public boolean accept(File path)
		{
			return path.getName().toLowerCase().endsWith("mp3");
		}
	}
	
	protected Document getDocument(File f) throws Exception
	{
		Document doc =  new Document();
		doc.add(new Field("filename",f.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field("fullpath",f.getCanonicalPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		
		return doc;
	}
	
	private void indexFile(File f) throws Exception
	{
		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc);
	}
	
	public long getTime()
	{
		return dauer;
	}
}