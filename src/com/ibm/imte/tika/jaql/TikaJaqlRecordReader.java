package com.ibm.imte.tika.jaql;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.MultiFileSplit;
import org.apache.hadoop.mapred.RecordReader;

import com.ibm.imte.tika.TikaHelper;
import com.ibm.jaql.io.hadoop.JsonHolder;
import com.ibm.jaql.io.hadoop.JsonHolderDefault;
import com.ibm.jaql.json.type.BufferedJsonRecord;
import com.ibm.jaql.json.type.JsonString;

/**
 * Similar to the TikaRecordReader. The main difference is that we do not output
 * standard key,value pairs but a JsonObject containing these values. JSON is
 * the internal data structure of Jaql.
 */
@SuppressWarnings("deprecation")
public class TikaJaqlRecordReader implements
		RecordReader<JsonHolder, JsonHolder>
{

	private MultiFileSplit split;
	private FileSystem fs;
	private int count = 0;
	private Path[] paths;
	private boolean done = false;
	private TikaHelper tikaHelper;
	private Configuration conf;

	public TikaJaqlRecordReader(Configuration conf, MultiFileSplit split)
			throws IOException
	{

		this.split = split;
		this.conf = conf;
		this.paths = split.getPaths();
		this.tikaHelper = new TikaHelper(conf);

	}

	public void close() throws IOException
	{
	}

	public long getPos() throws IOException
	{
		return 0;
	}

	public float getProgress() throws IOException
	{
		return done ? 0.0f : 1.0f;
	}

	public boolean next(JsonHolder key, JsonHolder value) throws IOException
	{

		if (count >= split.getNumPaths())
		{
			done = true;
			return false;
		}

		Path file = paths[count];
		fs = file.getFileSystem(conf);
		InputStream stream = fs.open(file);

		BufferedJsonRecord bjr = new BufferedJsonRecord();

		bjr.setNotSorted();
		bjr.add(new JsonString("path"), new JsonString(file.getName()));

		bjr.add(new JsonString("content"),
				new JsonString(this.tikaHelper.readPath(stream)));

		value.setValue(bjr);

		stream.close();

		count++;

		return true;
	}

	public JsonHolder createKey()
	{
		return new JsonHolderDefault();
	}

	public JsonHolder createValue()
	{
		return new JsonHolderDefault();
	}
}
