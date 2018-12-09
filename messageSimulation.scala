import org.apache.spark.GraphUtil
import org.apache.spark.graphx._
import org.apache.spark.SparkContest
import org.apache.spark.SparkConf
import org.apache.spark.Logging

/*
input: graphs with vertices marked as active or inactive
       number of iterations
output: spread size
        graphs with active mark

*/


Object Experiment extends Logging {

  //status of the graph
  val ACTIVE: long = 1
  val IN_ACTIVE: long = 0
  val VISITED: long = 2
  val IGNORE: long = -1;

  def spread(graph: Graph[long, Double],

              activeNodes: List[VertexId],
              iterations: Int): Double = {

    val icGraph = graph.mapVertices {

      (id, attr) =>

        if (activeNodes.contains(id))
          ACTIVE
        else
          IN_ACTIVE

    }.cache()

    def vertexProgram(id: VertexId, attr: Long, msg: Long): Long = {
      if (msg == IGNORE) {
        attr
      }
      else if (attr == IN_ACTIVE) {
        msg
      }
      else if (attr == ACTIVE) {
        VISITED
      }
      else {
        attr
      }
    }

    def sendMessage(edge: EdgeTriplet[Long, Double]) = {

      if (edge.dstAttr == ACTIVE || edge.dstAttr == VISITED) {

        Iterator.empty

      } else if (edge.srcAttr == ACTIVE) {

        if (math.random <= edge.attr) {

          Iterator((edge.dstId, ACTIVE))

        } else {

          Iterator.empty

        }

      } else {

        Iterator.empty

      }
    }

    def messageCombiner(a: Long, b: Long) = ACTIVE

    val initialMessage = IGNORE

    var iter = 0
    var sum = 0L

    while (iter < iterations) {

      sum += Pregel(icGraph, initialMessage,
        activeDirection = EdgeDirection.Out)(vertexProgram, sendMessage, messageCombiner)
        .vertices.filter(vd => vd._2 == ACTIVE || vd._2 == VISITED)
        .count

      iter += 1

      if (iter % 50 == 0) {
        println("iter : " + iter)
      }
    }

    sum.toDouble / iterations.toDouble
  }


  // get the graph
  // filter all active vertices
  // try to active along edges
  // change the attr of active vertices to some other constant
  // do until no vertices are "active" anymore
  // return number of vertices activated in the process

  def main(args: Array[String]) {

    val start = System.currentTimeMillis
    val conf = new SparkConf().setAppName("Experiment")
    val sc = new SparkContext(conf)
    val inputGraphFile = args(0)
    val activeNodesFile = args(1)
    val iterations = args(2).toInt
    println("Number of Simulations: " + iterations)
    val prob = args(3).toDouble
    println("Propagation Probability : " + prob)

    val activeNodes = sc.textFile(activeNodesFile)
      .flatMap(l => l.split(','))
      .map(l => l.toLong)
      .collect()
      .toList

    println("Number of Initial Active Nodes " + activeNodes.length)
    val graph = GraphUtil.undirected(GraphUtil.mapTypes(GraphLoader.edgeListFile(sc, inputGraphFile)), prob)

    val spread = run(graph, activeNodes, iterations)
    println("Total Spread: " + spread)

    var totalTime = System.currentTimeMillis - start
    println("Total Time : " + totalTime.toDouble / 1000)
  }

}














