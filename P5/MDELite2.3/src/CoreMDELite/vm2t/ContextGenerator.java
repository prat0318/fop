package CoreMDELite.vm2t;

import org.apache.velocity.VelocityContext;


public interface ContextGenerator {
  VelocityContext generateContext(Model m);
}
