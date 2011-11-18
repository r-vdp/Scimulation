package core.statistics

import scala.collection.mutable.Map

object CSVOutputStatistics {

  def generateCsvFile(map: Map[Int, Int], sFileName: String) {
    try {
      val writer = new java.io.FileWriter(sFileName);
      writer.append("\"Time\"")
      writer.append(';')
      writer.append("\"Count\"")
      writer.append('\n')
      for (time <- map.keySet) {
        writer.write(time.toString)
        writer.append(';')
        writer.write((map.get(time) getOrElse 0).toString)
        writer.append('\n')
      }
      writer.flush();
      writer.close();
    }
  }
}
