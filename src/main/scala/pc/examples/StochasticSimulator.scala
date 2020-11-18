package pc.examples
import pc.utils.Time
import pc.modelling.CTMCSimulation
import java.util.Random

object StochasticSimulator extends App{
  import pc.examples.StochasticChannel.state._

  val channel = StochasticChannel.stocChannel

  val channelAnalysis = CTMCSimulation(channel)
  Time.timed{
    val timedList = channelAnalysis.newSimulationTrace(IDLE, new Random)
      .take(10)
      .toIterator


    //timedList.map( v => (v.n))
  }
}


