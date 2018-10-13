package hr.fer.zemris.optjava.generic.ga;

import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.generic.ga.provimpl.ThreadLocalEvaluator;
/**
 * Class which implements {@linkplain Runnable} and implements job for evaluating picture
 *  
 * @author Branko
 *
 */
public class Consumer implements Runnable {
	   /**
	    * private instance of {@linkplain ConcurrentLinkedQueue} which is source queue
	    */
	   private ConcurrentLinkedQueue<GASolutionVector> queue;
	   
	   private IGAEvaluator<int[]> evalu; 
	   
	   /**
	    * Public constructor accepts desire settings
	    * @param queue desire instance of {@linkplain ConcurrentLinkedQueue}
	    * @param template desire template of {@linkplain GrayScaleImage}
	    */
	   Consumer(ConcurrentLinkedQueue<GASolutionVector> queue, GrayScaleImage template, IGAEvaluator<int[]> evalu){
	      this.queue = queue;
	      this.evalu = evalu;

//	      this.evalu = new ThreadLocalEvaluator(template);
	   }
	   
	   @Override
	   public void run() {
	      GASolutionVector vec;

	         while ((vec = queue.poll()) != null) {
	        	 evalu.evaluate(vec);
	        	 
	         }

	      
	   }
}
