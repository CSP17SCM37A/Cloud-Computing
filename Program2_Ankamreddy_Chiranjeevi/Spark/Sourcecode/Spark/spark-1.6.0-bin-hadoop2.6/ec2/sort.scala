val start_time = System.currentTimeMillis()
val inputfile=sc.textFile("hdfs:////sparkdata")
val sort_file=inputfile.map(line => (line.take(10), line.drop(10)))
val sort = sort_file.sortByKey()
val lines=sort.map {case (key,value) => s"$key $value"}
lines.saveAsTextFile("/sparkdata/output")
val end_time = System.currentTimeMillis()
println ("Total time :" + (end_time - start_time) + "ms")
