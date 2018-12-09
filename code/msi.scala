import org.apache.log4j.{Level, LogManager}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD


    val edges = links.map { line =>
  		val fields = line.split('\t')
  		Edge(fields(0).toLong, fields(1).toLong, 0)
	}

    //val socialgraph = Graph(vertices, relationships)
    val socialgraph : Graph[Any, String] = Graph.fromEdges(edges, "defaultProperty")



val graph = GraphLoader.edgeListFile(sc, "edge.txt")
// Find the connected components
val cc = graph.connectedComponents().vertices
val nodes = sc.textFile("nodes.txt").map { line =>
  val fields = line.split(",")
  (fields(0).toLong, fields(1))
}
val ccByUsername = nodes.join(cc).map {
  case (id, (idex, cc)) => (idex, cc)
}
// Print the result
println(ccByUsername.collect().mkString("\n"))


def rcvMsg(id: VertexId, value: Long, mesage: Long): Long = {
      if (msg == ?) {
        value
      }
      else if (value == NOT_ACTIVE) {
        message
      }

    }

def sendMessage(edge: EdgeTriplet[(Long, List[VertexId]), Double]) = {
      val x = edge.srcAttr._2.filterNot(edge.dstAttr._2.toSet)
      println(x)
      if (x.size > 0) {
        Iterator((edge.dstId, x))
      }
      else {
        Iterator.empty
      }
    }


def mergeMsg(msg1: (String, Float), msg2: (String, Float)): (String, Float) = {
      if (Math.random() < msg1._2)
        return (msg1._1, 1f)
      else if (Math.random() < msg2._2)
        return (msg2._1, 1f)
      return (msg1._1, 0f)
    }


socialgraph.cache()
    val mGraph = socialgraph.pregel(initialMsg,
      Integer.MAX_VALUE,
      EdgeDirection.Out)(
      rcvMsg,
      sendMsg,
      mergeMsg)
