package core.statistics

import java.io.FileWriter
import java.io.IOException


object CSVOutputStatistics extends App {

  val map : Map[Int, Int] = Map((1, 5), (2, 10), (3, 15), (4, 20), (5 , 25 ))
  val test:String = "test.csv"
  generateCsvFile(map, test)
  





 def generateCsvFile(map: Map[Int, Int], sFileName:String)
  {
        try
        {
            val writer = new java.io.FileWriter(sFileName);
            writer.append("\"Time\"")
            writer.append(';')
            writer.append("\"Count\"")
            writer.append('\n')
            for (time<-map.keySet){
                                writer.write(time.toString())
                                writer.append(';')
                                writer.write((map.get(time) getOrElse 0).toString())
                                writer.append('\n')
            }                   
            writer.flush();
            writer.close();
        }

   }

}