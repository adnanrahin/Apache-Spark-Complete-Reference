package org.apache.spark.listhdfs

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}

import java.net.URI
import scala.collection.mutable.ListBuffer

class ListHdfsFiles() {

  def listHdfsFiles(directory: String): ListBuffer[FileStatus] = {
    val fs = FileSystem.get(new URI("hdfs://localhost:9000/"), new Configuration())

    val filePath = new Path("/" + directory)

    val remoteIterator = fs.listFiles(filePath, true)

    val readableFiles = new ListBuffer[FileStatus]()

    while (remoteIterator.hasNext) {
      readableFiles += remoteIterator.next()
    }
    readableFiles
  }

}
