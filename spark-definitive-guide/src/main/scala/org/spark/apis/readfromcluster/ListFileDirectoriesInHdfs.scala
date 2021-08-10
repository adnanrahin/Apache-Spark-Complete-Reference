package org.spark.apis.readfromcluster

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}
import org.apache.spark.sql.SparkSession

import java.io.{BufferedReader, InputStreamReader}
import java.net.URI
import java.sql.Date
import scala.collection.mutable.ListBuffer

object ListFileDirectoriesInHdfs {

  val RETENTION = "Retention"
  val CHECKBACKUP = "CheckBackup"
  val NUMSFILES = "NumsFiles"

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("ListFileDirectoriesInHdfs")
      .getOrCreate()

    val fs = FileSystem.get(new URI("hdfs://localhost:9000/"), new Configuration())

    val filePath = new Path("/data")

    val remoteIterator = fs.listFiles(filePath, true)

    val readableFiles = new ListBuffer[FileStatus]()

    while (remoteIterator.hasNext) {
      readableFiles += remoteIterator.next()
    }

    val fileTreeMap = readableFiles
      .groupBy(_.getPath.getParent.toString)

    val metaList = fileTreeMap.flatMap(directory => {
      directory._2.filter(file => file.getPath.toString.endsWith(".meta")
        && readMetaFile(fs, file.getPath).contains(NUMSFILES))
    }).toList

    println(metaList.foreach(file => println(file.getPath)))

    println("Test Script Running")

    println(fileTreeMap.foreach(f => println(f._1)))

    val filterOutNonMetaFileDirectory =
      fileTreeMap
        .filterKeys(key => filterKeyIfMatch(metaList, key))


    println(metaList.size + " " + filterOutNonMetaFileDirectory.size)

    val filterFileSystem = filterOutNonMetaFileDirectory.flatMap {
      case (_, files) => files.find(x => x.getPath.toString.endsWith(".meta")) match {
        case Some(file) =>
          val index = Integer.parseInt(readMetaFile(fs, file.getPath).getOrElse(NUMSFILES, 0.toString))
          if (index != 0) {
            files.takeRight(index)
          } else None
        case None => files
      }
    }

    filterFileSystem.foreach(f => println(f.getPath + " : " + new Date(f.getModificationTime) ))

  }

  def createFileTree(readableFiles: ListBuffer[FileStatus]): Map[String, ListBuffer[FileStatus]] = {
    val fileSystemMap = readableFiles
      .groupBy(_.getPath.getParent.toString)
    fileSystemMap.map(f => f._2.sortBy(_.getModificationTime))
    fileSystemMap
  }

  def readMetaFile(fs: FileSystem, path: Path): Map[String, String] = {
    val bufferedReader = new BufferedReader(new InputStreamReader(fs.open(path)))
    try {
      Stream.continually(bufferedReader.readLine()).takeWhile(_ != null).flatMap(l => {
        val tokens = l.split(":").map(_.trim)
        tokens(0) match {
          case RETENTION => Option(RETENTION, tokens(1))
          case CHECKBACKUP => Option(CHECKBACKUP, tokens(1))
          case NUMSFILES => Option(NUMSFILES, tokens(1))
          case _ => None
        }
      }).toMap
    }
    finally {
      bufferedReader.close()
    }
  }

  def filterKeyIfMatch(metaList: List[FileStatus], key: String): Boolean = {
    val isMetListContainKey = metaList.exists(_.getPath.toString.startsWith(key + "/.meta"))
    isMetListContainKey
  }

}
