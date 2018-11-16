import org.apache.log4j.{Level, LogManager}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

//a start example to use graph modified from spark example.
object SocalialIM {
  val conf = new SparkConf().setAppName("SparkGrep")
  val sc = new SparkContext(conf)

  val log = LogManager.getRootLogger
  log.setLevel(Level.WARN)

  def main(args: Array[String]): Unit = {
    
    val vertices: RDD[(VertexId, (String,float))] =
      sc.parallelize(Array((1L, ("A",0.7f)), (2L, ("B",0.4f)),
        (3L, ("C", 0.5f)), (4L, ("D", 0.6f)), (5L, ("E", 0.6f))))


    val relationships: RDD[Edge[String]] =
      sc.parallelize(Array(Edge(1L, 2L, "friends"), Edge(2L, 1L, "friends"), Edge(1L, 3L, "friends"), Edge(3L, 1L, "friends"),
        Edge(2L, 4L, "friends"), Edge(4L, 2L, "friends"), Edge(3L, 4L, "friends"), Edge(4L, 3L, "friends"), Edge(1L, 5L, "friends"), Edge(5L, 1L, "friends")))

    val socialgraph = Graph(vertices, relationships)

    val initialMsg = ("A", 999f)

    def rcvMsg(vertexId: VertexId, value: (String, Float), message: (String, Float)): (String,  Float) = {
      val log = LogManager.getRootLogger
      log.setLevel(Level.ERROR)

      if (message == initialMsg || value._2) {
        if (value._1.equals(initialMsg._1)) {
          return (value._1, true, value._3)
        }
        value
      }
      else {
        if ( Math.random().toFloat< message._2) {
          (value._1, true, value._3)
        }
        else
          value
      }
    }

    def sendMsg(triplet: EdgeTriplet[(String, Float), String]): Iterator[(VertexId, (String, Float))] = {
      val sourceVertex = triplet.srcAttr
      val sourceIsActive: Boolean = triplet.srcAttr._2
      val log = LogManager.getRootLogger
      if (!sourceIsActive || triplet.dstAttr._2)
        Iterator.empty
      else {
        Iterator((triplet.dstId, (triplet.srcAttr._1, sourceVertex._3)))
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


    val count = mGraph.vertices.filter(p => p._2._2)
    println(count.count())

  }
}
