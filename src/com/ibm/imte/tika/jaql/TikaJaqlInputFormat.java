package com.ibm.imte.tika.jaql;

import java.io.IOException;

//import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MultiFileInputFormat;
import org.apache.hadoop.mapred.MultiFileSplit;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

import com.ibm.jaql.io.hadoop.JsonHolder;

/**
 * The Jaql equivalent to the TikaInputFormat. Uses the older MapReduce
 * framework but does the same
 */
@SuppressWarnings("deprecation")
public class TikaJaqlInputFormat extends
		MultiFileInputFormat<JsonHolder, JsonHolder>
{

	@Override
	public RecordReader<JsonHolder, JsonHolder> getRecordReader(
			InputSplit split, JobConf job, Reporter reporter)
			throws IOException
	{
		return new TikaJaqlRecordReader(job, (MultiFileSplit) split);
	}

	@Override
	protected boolean isSplitable(FileSystem fs, Path filename)
	{
		return false;
	}
}
