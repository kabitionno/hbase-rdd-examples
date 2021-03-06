HBase RDD examples
==================

![logo](https://raw.githubusercontent.com/unicredit/hbase-rdd/master/docs/logo.png)

This is an example project for [HBase RDD](https://github.com/unicredit/hbase-rdd). It currently runs on CDH 5.3, although it will run on other versions of CDH with minor modifications.

Running
-------

First, build the project with

    sbt assembly

This will generate `target/scala-2.10/hbase-rdd-examples-assembly-0.4.3.jar`.

You can then copy this file, together with the files in the `scripts` directory, on a gateway machine of the cluster, and then run the scripts to launch the jobs. Of course, you may have to adapt some parameters in the scripts.

Jobs
----

We assume the existence of a file `test-input` on the user directory on HDFS of the user running the job. Each line contains four fields, each one being a random printable string of length 10. Let us call `k`, `col1`, `col2`, `col3` the four fields.

**WriteSingleCf** copies the contents of this file inside `test-table`, using `k` as rowkey, putting `col1` and `col2` under the column family `cf1` and discarding `col3`.

**WriteMultiCf** copies the contents of this file inside `test-table`, using `k` as rowkey, putting `col1` and `col2` under the column family `cf1` and `col3` under `cf2`.

**WriteBulk** does the same as **WriteSingleCf** (on table `test-table-bulk`), by writing to HFiles on HDFS and then submitting these HFiles to the HBase servers.

All the write jobs create the table if it does not exist already - **WriteBulk** also takes care of computing splits appropriate for the file contents and a desired region size (128M in the example).

**Read** read the contents of `test-tables` and reassambles a TSV output on HDFS under `test-output`, in the same format as the original.

Test file
---------

You can generate the test file as you prefer. A quick way would be opening a Scala console and writing

    import java.io._
    import scala.util.Random
    def printToFile(path: String)(op: PrintWriter => Unit) {
      val p = new PrintWriter(new File(path))
      try { op(p) } finally { p.close() }
    }

    def nextString = (1 to 10) map (_ => Random.nextPrintableChar) mkString
    def nextLine = (1 to 4) map (_ => nextString) mkString "\t"

    printToFile(args(0)) { p =>
      for (_ <- 1 to 100000000) {
        p.println(nextLine)
      }
    }