tika-jaql-hadoop-mapreduce
==========================

Apache Tika integration with Jaql using MapReduce for Hadoop


This project helps to get over the inefficiency of processing multiple small files in Hadoop using Jaql. Moreover, it allows for processing and analysis of binary documents in Hadoop using Apache Tika by integrating it in Jaql which will in turn spawn a MapReduce job.

An article about 'Processing and Content Analysis of Various Document Types using MapReduce and IBM BigInsights' is currently in publishing and comming soon. Link will be available shortly will full descriptions and usage of the code.


This project contains:
• src: the source folder with the Java classes needed for the Jaql module.
• tika: the module folder for Jaql.
• tika_run: a set of sample Jaql script files for common tasks like loading, transforming, and text analytics.
• tika_run/data/CameraReviews: a set of sample demo word (.doc) files with simple camera reviews.
• tika_run/EmotiveTone.tam: a compiled IBM text analytics module developed in AQL for the extraction of emotive tones.

Setup steps necessary for using the Jaql module:
- Import the Eclipse project to the BigInsights development studio
- Export the Java project as tika-jaql.jar to tika/lib/ (if no changes have been made, this step is not necessary)
- Copy the tika (module) folder to $JAQL_HOME/modules on your cluster
- Go to the tika_run directory and upload the reviews to hdfs `hadoop fs -put tika_run/data /tmp/`
- run the Jaql scripts through the jaqlshell,
$JAQL_HOME/bin/jaqlshell -b tika-jaql_Load.jaql
$JAQL_HOME/bin/jaqlshell -b tika-jaql_Transform.jaql
$JAQL_HOME/bin/jaqlshell -b tika-jaql_TextAnalytics.jaql
